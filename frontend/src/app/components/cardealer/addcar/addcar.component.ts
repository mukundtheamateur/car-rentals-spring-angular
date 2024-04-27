import { Component } from '@angular/core';
import { CookieServices } from '../../../services/cookie.service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterLinkActive } from '@angular/router';
import { CarService } from '../../../services/car.service';
import { DashboardComponent } from '../../dashboard/dashboard.component';
import * as toastr from 'toastr';
@Component({
  selector: 'app-addcar',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, DashboardComponent, RouterLinkActive],
  templateUrl: './addcar.component.html',
  styleUrl: './addcar.component.css',
})
export class AddcarComponent {

  dealerId: any;
  role: any;
  constructor(
    private carService: CarService,
    private cookie: CookieServices,
    private fb: FormBuilder,
    private router: Router
  ) {}

  carAddForm = this.fb.group({
    carName: ['Porche 911 Carerra', Validators.required],
    price: ['180880', Validators.required],
    deposit: ['2000', Validators.required],
    fuelType: ['Petrol', Validators.required],
    gearbox: ['Automatic', Validators.required],
    image: ['https://gohype.in/_next/image?url=https%3A%2F%2Fik.imagekit.io%2Fegrcxziyv%2Fhypeluxury%2Frent_porsche_911_carrera_3.png&w=3840&q=75', Validators.required],
    seats: ['2', Validators.required],
    doors: ['2', Validators.required],
    fuelpolicy: ['AlreadyFilled', Validators.required],
    mileage: ['12', Validators.required],
    cancellation: ['10000', Validators.required],
    amendments: ['10000', Validators.required],
    theftProtection: ['2000', Validators.required],
    collisionDamage: ['1000', Validators.required],
    fullInsurance: ['4000', Validators.required],
    additionalDriver: ['11000', Validators.required],
    wheelDrive: ['FWD', Validators.required],
  });

  ngOnInit(){
    this.dealerId = JSON.parse(this.cookie.getCookie('user') || '');
    if (this.dealerId.role.id ==1) {
      this.role = 'user';
    }else{
      this.role='dealer';
    }
  }

  onSubmit() {
    if (this.carAddForm.valid) {
      const newCar = {
        carName: this.carAddForm.value.carName,
        price: this.carAddForm.value.price,
        deposit: this.carAddForm.value.deposit,
        fuelType: this.carAddForm.value.fuelType,
        gearbox: this.carAddForm.value.gearbox,
        image: this.carAddForm.value.image,
        seats: this.carAddForm.value.seats,
        doors: this.carAddForm.value.doors,
        fuelpolicy: this.carAddForm.value.fuelpolicy,
        mileage: this.carAddForm.value.mileage,
        cancellation: this.carAddForm.value.cancellation,
        amendments: this.carAddForm.value.amendments,
        theftProtection: this.carAddForm.value.theftProtection,
        collisionDamage: this.carAddForm.value.collisionDamage,
        fullInsurance: this.carAddForm.value.fullInsurance,
        additionalDriver: this.carAddForm.value.additionalDriver,
        wheelDrive: this.carAddForm.value.wheelDrive,
        dealer: {
          id: this.dealerId.id
        }
      };
      this.addCar(newCar);
    }
  }

  addCar(newCar: any) {
    this.carService.saveCar(newCar).subscribe(
      (response: any) => {
        toastr.options.positionClass = 'toast-bottom-right';
        toastr.success('Car added successfully');
        console.log(response.body);
        this.router.navigate(['/mycars']);
      },
      (error: any) => {
        toastr.options.positionClass = 'toast-bottom-right';
        toastr.error("Car can't be added: " + error.message);
        this.router.navigate(['/addCar']);
      }
    );
  }
}
