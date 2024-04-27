import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CarService } from './../../../services/car.service';
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Car } from '../../../models/car';
import { CookieServices } from '../../../services/cookie.service';
import { DashboardComponent } from '../../dashboard/dashboard.component';
import * as toastr from 'toastr';
@Component({
  selector: 'app-cars',
  standalone: true,
  imports: [DashboardComponent, CommonModule, ReactiveFormsModule],
  templateUrl: './cars.component.html',
  styleUrl: './cars.component.css',
})
export class CarsComponent {
  filterForm = this.formBuilder.group({
    minPrice: [],
    maxPrice: [],
  });
  filterCarNameForm = this.formBuilder.group({
    carName: [''],
  });
  cars: Car[] = [];
  user: any = JSON.parse(this.cookie.getCookie('user') || '');
  constructor(
    private carService: CarService,
    private cookie: CookieServices,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    if (!this.user) {
      this.router.navigate(['/login']);
      return;
    }
    console.log(this.user.id);
    this.carService.getAllCars().subscribe((cars) => {
      this.cars = cars;
    });
  }

  bookCar(id: number): void {
    this.router.navigate([`/bookings/${id}`]);
  }

  filterCars() {
    this.carService
      .getCarsByPriceRange(
        this.filterForm.value.minPrice,
        this.filterForm.value.maxPrice
      )
      .subscribe((response: any) => {
        toastr.options.positionClass = 'toast-bottom-right';
        toastr.success('Cars filtered successfully');
        this.cars = response;
        this.router.navigate(['/cars']);
      });
  }
  filterByCarName() {
    this.carService
      .getCarsByCarName(
        this.filterCarNameForm.value.carName
      )
      .subscribe((response: any) => {
        toastr.options.positionClass = 'toast-bottom-right';
        toastr.success('Cars filtered successfully');
        this.cars = response;
        this.router.navigate(['/cars']);
      },(error:any)=>{
        toastr.options.positionClass = 'toast-bottom-right';
        toastr.info('Cars Not found with car name: '  + this.filterCarNameForm.value.carName);
      });
  }
  showSidebar = false;

toggleSidebar() {
  this.showSidebar = !this.showSidebar;
  let mainContent:any = document.getElementById('outer');
  if (this.showSidebar) {
    mainContent.classList.add('blur');
  } else {
    mainContent.classList.remove('blur');
  }
}
}
