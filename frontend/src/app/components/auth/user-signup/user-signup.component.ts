import { UserService } from './../../../services/user.service';
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import * as toastr from 'toastr';

@Component({
  selector: 'app-user-signup',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './user-signup.component.html',
  styleUrl: './user-signup.component.css'
})
export class UserSignupComponent {

  constructor(private fb: FormBuilder, private userService: UserService, private router: Router){

  }

  userSignupForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
    phone:[''],
    fullname:[''],
    dateOfBirth:[''],
    avatar:[''],
    city:['']
  })
  onSubmit() {
    if (this.userSignupForm.valid) {
      const user = {
        email: this.userSignupForm.value.email,
        password: this.userSignupForm.value.password,
        phone: this.userSignupForm.value.phone,
        fullname: this.userSignupForm.value.fullname,
        dateOfBirth: this.userSignupForm.value.dateOfBirth,
        avatar: this.userSignupForm.value.avatar,
        city: this.userSignupForm.value.city,
        createdBy: this.userSignupForm.value.fullname,
        updatedBy: this.userSignupForm.value.fullname,
        role:{
          id:1
        }
      };
      this.userService.createUser(user).subscribe((response:any)=>{
        console.log("received response is ",response);
        localStorage.setItem('jwt', JSON.stringify(response.headers.get('jwt')));
        // localStorage.setItem('jwt', JSON.stringify(data))
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
