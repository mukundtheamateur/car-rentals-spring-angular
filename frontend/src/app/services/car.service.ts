import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Car } from '../models/car';

@Injectable({
  providedIn: 'root'
})
export class CarService {

  private apiUrl = 'http://localhost:8080/api/cars';

  constructor(private http: HttpClient) { }

  getAllCars(): Observable<Car[]> {
    return this.http.get<Car[]>(this.apiUrl);
  }

  getCarById(id: number): Observable<Car> {
    return this.http.get<Car>(`${this.apiUrl}/${id}`);
  }

  saveCar(car: Car): Observable<Car> {
    return this.http.post<Car>(`${this.apiUrl}/create`, car);
  }

  deleteCar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  updateCar(id: number, carUpdates: Car): Observable<Car> {
    return this.http.put<Car>(`${this.apiUrl}/update/${id}`, carUpdates);
  }

  getCarsByDealerId(dealerId: number): Observable<Car[]> {
    return this.http.get<Car[]>(`${this.apiUrl}/dealer/${dealerId}`);
  }

  getCarsByCarName(model: string | null | undefined): Observable<Car[]> {
    return this.http.get<Car[]>(`${this.apiUrl}/model/${model}`);
  }

  getCarsByPriceRange(minPrice: any, maxPrice: any ): Observable<Car[]> {
    return this.http.get<Car[]>(`${this.apiUrl}/price?minPrice=${minPrice}&maxPrice=${maxPrice}`);
  }
}
