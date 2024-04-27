import { CarDealerService } from './../../services/car-dealer.service';
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { CookieServices } from '../../services/cookie.service';
import { UserService } from '../../services/user.service';
import { DashboardComponent } from '../dashboard/dashboard.component';
import * as toastr from 'toastr';

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [
    RouterLink,
    DashboardComponent,
    CommonModule,
    RouterLinkActive,
    ReactiveFormsModule,
  ],
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css',
})
export class AccountsComponent {
  user: any;
  userRole: string ="";

  userUpdateForm = this.fb.group({
    fullname: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    phone: [''],
    dateOfBirth: [''],
    bio: [''],
    avatar: [''],
    city: [''],
  });

  carDealerUpdateForm = this.fb.group({
    dealerName: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    phone: [''],
    avatar: [''],
    allCities: [''],
  });

  constructor(
    private userService: UserService,
    private carDealerService: CarDealerService,
    private cookie: CookieServices,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.user = JSON.parse(this.cookie.getCookie('user') || '');

      if (this.user.role.id ==1) {
        this.userRole = 'user';
      this.userUpdateForm = this.fb.group({
        fullname: [this.user.fullname],
        email: [this.user.email],
        phone: [this.user.phone],
        dateOfBirth: [this.user.dateOfBirth],
        bio: [this.user.bio],
        avatar:[this.user.avatar],
        city: [this.user.city],
      });
    } else if (this.user.role.id ==2) {
      this.userRole = 'dealer';
      this.carDealerUpdateForm = this.fb.group({
        dealerName: [this.user.dealerName],
        email: [this.user.email],
        phone: [this.user.phone],
        avatar: [this.user.avatar],
        allCities: [this.user.allCities]
      });
    }
  }

  onSubmit() {
    if (this.userRole === 'user') {
      if (this.userUpdateForm.valid) {
        const newUser = {
          email: this.userUpdateForm.value.email,
          phone: this.userUpdateForm.value.phone,
          fullname: this.userUpdateForm.value.fullname,
          dateOfBirth: this.userUpdateForm.value.dateOfBirth,
          city: this.userUpdateForm.value.city,
          bio: this.userUpdateForm.value.bio,
          avatar: this.userUpdateForm.value.avatar,
          createdBy: this.userUpdateForm.value.fullname,
          updatedBy: this.userUpdateForm.value.fullname,
        };
        this.onUpdate(newUser);
      }
    } else if (this.userRole === 'dealer') {
      const newCarDealer = {
        dealerName: this.carDealerUpdateForm.value.dealerName,
        email: this.carDealerUpdateForm.value.email,
        phone: this.carDealerUpdateForm.value.phone,
        avatar: this.carDealerUpdateForm.value.avatar,
        allCities: this.carDealerUpdateForm.value.allCities
      }
      console.log(this.carDealerUpdateForm.value.avatar);

      this.onUpdate(newCarDealer);
    }
  }

  onUpdate(newUser: any): void {
    if(this.userRole === 'user'){
      this.userService.updateUser(this.user.id, newUser).subscribe(
        (response: any) => {
          this.user = response.body;
          this.updateCookie();
          toastr.options.positionClass = 'toast-bottom-right';
          toastr.success('User updated successfully');
          this.router.navigate(['/accounts']);
        },
        (error: any) => {
          toastr.options.positionClass = 'toast-bottom-right';
          toastr.error('User cant be updated: ' + error.message);
          this.router.navigate(['/accounts']);
        }
      );
    }
    else if(this.userRole === 'dealer'){
      this.carDealerService.updateCarDealer(this.user.id, newUser).subscribe(
        (response: any) => {
          this.user = response.body;
          this.updateCookie();
          toastr.options.positionClass = 'toast-bottom-right';
          toastr.success('Car Dealer updated successfully');
          this.router.navigate(['/accounts']);
        },
        (error: any) => {
          toastr.options.positionClass = 'toast-bottom-right';
          toastr.error('Car Dealer cant be updated: ' + error.message);
          this.router.navigate(['/accounts']);
        }
      );
    }
  }

  updateCookie() {
    if(this.userRole === 'user'){
      this.userService.getUserByEmail(this.userUpdateForm.value.email).subscribe(
        (response: any) => {
          console.log(response.body);
          this.cookie.setCookie('user', JSON.stringify(response.body));
        },
        (error: any) => {
          toastr.options.positionClass = 'toast-bottom-right';
          toastr.error('Can not fetch data : ' + error.message);
        }
      );
    }
    else if(this.userRole === 'dealer'){
      this.carDealerService.getCarDealerByEmailId(this.carDealerUpdateForm.value.email || '').subscribe(
        (response: any) => {
          console.log(response.body);
          this.cookie.setCookie('user', JSON.stringify(response.body));
        },
        (error: any) => {
          toastr.options.positionClass = 'toast-bottom-right';
          toastr.error('Can not fetch data : ' + error.message);
        }
      );
    }
  }
}
