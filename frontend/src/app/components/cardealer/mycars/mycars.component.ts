import { CarService } from './../../../services/car.service';
import { Component } from '@angular/core';
import { DashboardComponent } from '../../dashboard/dashboard.component';
import { CookieServices } from '../../../services/cookie.service';
import { Car } from '../../../models/car';
import { CommonModule } from '@angular/common';
import * as toastr from 'toastr';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-mycars',
  standalone: true,
  imports: [DashboardComponent, CommonModule, RouterLink],
  templateUrl: './mycars.component.html',
  styleUrl: './mycars.component.css',
})
export class MycarsComponent {
  cars: Car[] = [];
  dealerId: any = JSON.parse(this.cookie.getCookie('user') || '');
  constructor(
    private carService: CarService,
    private cookie: CookieServices,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (this.dealerId.role.id !== 2) {
      toastr.options.positionClass = 'toast-bottom-right';
      toastr.error("Only car dealers can access this page ");
      this.router.navigate(['/login']);
      return;
    }
    console.log(this.dealerId.id);
    this.carService.getCarsByDealerId(this.dealerId.id).subscribe((cars) => {
      this.cars = cars;
    });
  }

  deleteCar( id: number): void {
    this.carService.deleteCar(id).subscribe(
      (response: any) => {
        toastr.options.positionClass = 'toast-bottom-right';
        toastr.success("car deleted successfully",response.body);
        this.cars = this.cars.filter((car) => car.id !== id);
      },
      (error: any) => {
        toastr.options.positionClass = 'toast-bottom-right';
        toastr.error("Car can't be deleted: " + error.message);
        this.router.navigate(['/addCar']);
      }
    );
  }
}
