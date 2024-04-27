import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CarDealerService } from '../../../services/car-dealer.service';
import * as toastr from 'toastr'
@Component({
  selector: 'app-cardealer-signup',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './cardealer-signup.component.html',
  styleUrl: './cardealer-signup.component.css'
})
export class CardealerSignupComponent {
  constructor(private fb: FormBuilder, private carDealerService: CarDealerService, private router: Router){

  }

  carDealerSignupForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
    phone:[''],
    dealerName:[''],
    avatar:['../../../../assets/images/user.png'],
    allCities:['']
  })
  onSubmit() {
    if (this.carDealerSignupForm.valid) {
      const carDealer = {
        email: this.carDealerSignupForm.value.email,
        password: this.carDealerSignupForm.value.password,
        phone: this.carDealerSignupForm.value.phone,
        dealerName: this.carDealerSignupForm.value.dealerName,
        avatar: this.carDealerSignupForm.value.avatar,
        allCities: this.carDealerSignupForm.value.allCities,
        createdBy: this.carDealerSignupForm.value.dealerName,
        updatedBy: this.carDealerSignupForm.value.dealerName,
        role:{
          id:2
        }
      };
      this.carDealerService.registerCarDealer(carDealer).subscribe((response:any)=>{
        console.log("received response is ",response);
        localStorage.setItem('jwt', JSON.stringify(response.headers.get('jwt')));
        if(localStorage.getItem('jwt')){
          toastr.options.positionClass = 'toast-bottom-right';
          toastr.success("Signup successfull");
          this.router.navigate([''])
        }
      }, (error: any) => {
        toastr.options.positionClass = 'toast-bottom-right';
        toastr.error("An error occurred during signup: "+ error.message);
      })

    }
  }

}
