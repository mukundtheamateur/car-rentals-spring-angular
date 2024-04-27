import { CarDealerService } from './../../../services/car-dealer.service';
import { UserService } from './../../../services/user.service';
import { Component } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import * as toastr from 'toastr';
import { CookieServices } from '../../../services/cookie.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  loginForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
    role: [''],
  });

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router,
    private cookie: CookieServices,
    private carDealerService: CarDealerService
  ) {}

  fetchUser(email:string) {
    this.userService.getUserByEmail(email).subscribe(
      (response: any) => {
        console.log(response.body);
        this.cookie.setCookie('user', JSON.stringify(response.body));
        // if (this.cookie.getCookie('user')) {
          toastr.options.positionClass = 'toast-bottom-right';
          toastr.success('Login successful');
          this.router.navigate(['']);
        // }
      },
      (error: any) => {
        toastr.options.positionClass = 'toast-bottom-right';
        toastr.error('Can not fetch data : ' + error.message);
      }
    );
  }
  fetchCarDealer(email:string) {
    this.carDealerService.getCarDealerByEmailId(email).subscribe(
      (response: any) => {
        console.log(response.body);
        if (response.body) {
          this.cookie.setCookie('user', JSON.stringify(response.body));
          // if (this.cookie.getCookie('user')) {
            toastr.options.positionClass = 'toast-bottom-right';
            toastr.success('Login successful');

            this.router.navigate(['']);

          // }
        } else {
          toastr.options.positionClass = 'toast-bottom-right';
          toastr.error('No car dealer data received');
        }
      },
      (error: any) => {
        toastr.options.positionClass = 'toast-bottom-right';
        toastr.error('Can not fetch data : ' + error.message);
      }
    );
  }


  onSubmit() {
    if (this.loginForm.valid) {
      const user = {
        username: this.loginForm.value.email,
        password: this.loginForm.value.password,
        role: this.loginForm.value.role,
      };

      if (user.role === '1') {
        this.userService
          .login({
            username: this.loginForm.value.email,
            password: this.loginForm.value.password,
          })
          .subscribe(
            (response: any) => {
              localStorage.setItem(
                'jwt',
                JSON.stringify(response.headers.get('jwt'))
              );

              this.fetchUser(this.loginForm.value.email || '');
              // if (this.cookie.getCookie('user')) {
              //   toastr.options.positionClass = 'toast-bottom-right';
              //   toastr.success('Login successful');
              //   this.router.navigate(['']);
              // }
            },
            (error: any) => {
              toastr.options.positionClass = 'toast-bottom-right';
              toastr.warning(
                'Incorrect username or password: ' + error.message
              );
            }
          );
      } else {
        this.carDealerService
          .loginCarDealer({
            username: this.loginForm.value.email,
            password: this.loginForm.value.password,
          })
          .subscribe(
            (response: any) => {
              localStorage.setItem(
                'jwt',
                JSON.stringify(response.headers.get('jwt'))
              );

              this.fetchCarDealer(this.loginForm.value.email || '');
              // if (this.cookie.getCookie('user')) {
              //   toastr.options.positionClass = 'toast-bottom-right';
              //   toastr.success('Login successful');
              //   this.router.navigate(['']);
              // }
            },
            (error: any) => {
              toastr.options.positionClass = 'toast-bottom-right';
              toastr.warning(
                'Incorrect username or password: ' + error.message
              );
            }
          );
      }
    }
  }
}
