import { CarDealer } from './../../../models/carDealer';
import { BookingsService } from './../../../services/bookings.service';
import { CookieServices } from './../../../services/cookie.service';
import { Bookings, StatusType } from './../../../models/bookings';
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { DashboardComponent } from '../../dashboard/dashboard.component';
import * as toastr from 'toastr';
@Component({
  selector: 'app-myorders',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, DashboardComponent, CommonModule],
  templateUrl: './myorders.component.html',
  styleUrl: './myorders.component.css'
})
export class MyordersComponent {

  carDealerId:any;
  bookings:Bookings[] | any=[];
  changeStatusForm = this.fb.group({
    status:['']
  })
  constructor(private fb: FormBuilder,private cookie:CookieServices, private router:Router, private bookingsService:BookingsService){}
  ngOnInit(): void {
    this.carDealerId = JSON.parse(this.cookie.getCookie('user')).id;
    this.bookingsService.getBookingsByCarDealerId(this.carDealerId).subscribe((bookings)=>{
      this.bookings = bookings;
    });
  }
  bookingId:any;
  getCurrentBookingId(bookingId:any):void{
    this.bookingId = bookingId;

  }
  changeStatus(){
      if(this.changeStatusForm.valid){
        const booking = {
          id:this.bookingId,
          status: this.changeStatusForm.value.status
        };

        console.log(booking.id);
        console.log();

        this.bookingsService.updateBooking(booking).subscribe((response:any)=>{
          toastr.options.positionClass = 'toast-bottom-right';
          toastr.success("Booking status successfully changed to "+ response.status);
        },(error:any)=>{
          toastr.options.positionClass = 'toast-bottom-right';
          toastr.success("Failed. status type is not valid ");
        })
      }

  }

}
