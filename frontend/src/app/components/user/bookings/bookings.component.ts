import { Address } from './../../../models/Address';
import { Bookings, StatusType } from './../../../models/bookings';
import { CarDealer } from './../../../models/carDealer';
import { CookieServices } from './../../../services/cookie.service';
import { CarService } from './../../../services/car.service';
import { BookingsService } from './../../../services/bookings.service';
import {
  ActivatedRoute,
  Router,
  RouterLink,
  RouterLinkActive,
} from '@angular/router';
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { DashboardComponent } from '../../dashboard/dashboard.component';
import { catchError, of } from 'rxjs';
import * as toastr from 'toastr';
import { FormBuilder, FormControl, FormControlDirective, FormGroup, FormsModule, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { AddressService } from '../../../services/address.service';

@Component({
  selector: 'app-bookings',
  standalone: true,
  imports: [
    DashboardComponent,
    CommonModule,
    RouterLink,
    RouterLinkActive,
    ReactiveFormsModule,
    FormsModule,
  ],
  templateUrl: './bookings.component.html',
  styleUrl: './bookings.component.css',
})
export class BookingsComponent {

    dateRangeValidator: any = (control: FormGroup): ValidationErrors | null => {
    const fromDate = control.get('fromDate');
    const toDate = control.get('toDate');

    return fromDate && toDate && fromDate.value > toDate.value ? { 'dateRange': true } : null;
  };

  bookingForm = this.fb.group({
    fromDate: ['', Validators.required],
    toDate: ['', Validators.required],
    cancellation: [false ],
    amendments: [false],
    theftProtection: [false],
    collisionDamage: [false],
    fullInsurance: [false],
    additionalDriver: [false],
    address: ['', Validators.required]
  },{ validators: this.dateRangeValidator });
  constructor(
    private bookingsService: BookingsService,
    private carService: CarService,
    private router: Router,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private cookie: CookieServices,
    private addressService: AddressService
  ) {}

  slug: any;
  car: any;
  user: any;
  carDealer: any;
  address: any;
  addresses: Address[] = [];

  ngOnInit(): void {
    const slugParam = this.route.snapshot.paramMap.get('slug');
    this.slug = slugParam ? +slugParam : 0;

    this.user = JSON.parse(this.cookie.getCookie('user') || '');

    // fetch the detail of car and store it in car variable
    this.carService
      .getCarById(this.slug)
      .pipe(
        catchError((error) => {
          toastr.options.positionClass = 'toast-bottom-right';
          toastr.error(
            `Car with id ${this.slug} does not exist: `,
            error.error.message
          );
          this.router.navigate(['/cars']);
          return of(null);
        })
      )
      .subscribe((car) => {
        if (car) {
          this.car = car;
          console.log(this.car);
        }
      });

      this.addressService.getAllAddresses().subscribe((addresses)=>{
        this.addresses= addresses;
      },(error)=>{
        toastr.error("Pickup address not available. error: ",error.message);
      });

  }


   calculateTotalPrice(): number {
    // Calculate the new price based on the selected value
    // Return the new price
    let amount=0;
    amount = amount + this.car.price;
    if(this.bookingForm.value.cancellation){
      amount = amount + this.car.cancellation;
    }
    if(this.bookingForm.value.amendments){
      amount = amount + this.car.amendments;
    }
    if(this.bookingForm.value.theftProtection){
      amount = amount + this.car.theftProtection;
    }
    if(this.bookingForm.value.fullInsurance){
      amount = amount + this.car.fullInsurance;
    }
    if(this.bookingForm.value.collisionDamage){
      amount = amount + this.car.collisionDamage;
    }
    if(this.bookingForm.value.additionalDriver){
      amount = amount + this.car.additionalDriver;
    }
    return amount;
  }

  bookCar() {
    if (this.bookingForm.valid) {
      let confirmBooking: boolean = false;
      confirmBooking = confirm("Your total price is : " + this.calculateTotalPrice() + ". Do you want to rent?");

      let booking = {
        fromDate: this.bookingForm.value.fromDate || '',
        toDate: this.bookingForm.value.toDate || '',
        cancellation: this.bookingForm.value.cancellation || false,
        amendments: this.bookingForm.value.amendments || false,
        theftProtection: this.bookingForm.value.theftProtection || false,
        collisionDamage: this.bookingForm.value.collisionDamage || false,
        fullInsurance: this.bookingForm.value.fullInsurance || false,
        additionalDriver: this.bookingForm.value.additionalDriver || false,
        user: {
          id: this.user.id
        },
        car: {
          id: this.car.id
        },
        carDealer: {
          id:this.car.dealer.id
        },
        address: {
          id: this.bookingForm.value.address
        },
        createdBy: this.user.email,
        updatedBy: this.user.email,
        status: StatusType.reserved,
        price: this.calculateTotalPrice(),
        cancelRequest: false
      };


      if(confirmBooking){
        this.bookingsService.saveBooking(booking).subscribe(
          (response) => {
            console.log('Booking saved successfully', response);
            toastr.options.positionClass = 'toast-bottom-right';
            toastr.success('Booking created successfully');
            // Navigate to another page or show a success message
          },
          (error) => {
            console.error('Error saving booking', error);
            toastr.options.positionClass = 'toast-bottom-right';
            toastr.error('Booking cant be created');
            console.log(booking);
            // Handle the error
          }
        );
      }
    }
  }
}
