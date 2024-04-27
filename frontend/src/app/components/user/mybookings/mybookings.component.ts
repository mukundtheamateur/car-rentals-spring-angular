import { CommonModule } from '@angular/common';
import { Bookings } from './../../../models/bookings';
import { CookieService } from 'ngx-cookie-service';
import { BookingsService } from './../../../services/bookings.service';
import { Router,RouterLink } from '@angular/router';
import { Component } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { DashboardComponent } from '../../dashboard/dashboard.component';
import * as toastr from 'toastr';
@Component({
  selector: 'app-mybookings',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, DashboardComponent, CommonModule],
  templateUrl: './mybookings.component.html',
  styleUrl: './mybookings.component.css',
})
export class MybookingsComponent {
  userId: any;
  bookings: Bookings[] | any = [];
  constructor(
    private bookingsService: BookingsService,
    private cookieService: CookieService,
    private router:Router
  ) {}
  ngOnInit(): void {
    this.userId = JSON.parse(this.cookieService.get('user')).id;
    this.bookingsService
      .getBookingsByUserId(this.userId)
      .subscribe((bookings) => {
        this.bookings = bookings;
      });
  }
  cancelBooking(bookingId: any): void {
    this.bookingsService.deleteBooking(bookingId).subscribe(
      (response: any) => {
        toastr.options.positionClass = 'toast-bottom-right';
        toastr.success('Booking cancelled successfully', response.body);
        this.bookings = this.bookings.filter((booking: any) => booking.id !== bookingId);
        this.router.navigate(['/mybookings'])
      },
      (error: any) => {
        toastr.options.positionClass = 'toast-bottom-right';
        toastr.error('Booking cancellation failed: ' + error.message);
      }
    );
  }
}
