import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CarDealerService {
  private baseUrl = 'http://localhost:8080/api/cardealer';

  constructor(private http: HttpClient) { }

  getAllCarDealers(): Observable<any> {
    return this.http.get(`${this.baseUrl}`);
  }

  getCarDealerById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }
  getCarDealerByEmailId(email: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/email/${email}`,{observe: 'response',withCredentials: true});
  }

  logout(): void{
    localStorage.removeItem('jwt');
    sessionStorage.removeItem('user');
  }
  loginCarDealer(carDealer: any): Observable<any> {
    console.log("cardealer is ",carDealer)
    return this.http.post<any>(`http://localhost:8080/api/login/carDealer`, carDealer, {observe: 'response',withCredentials: true});
  }
  registerCarDealer(carDealer: Object): Observable<Object> {
    return this.http.post(`http://localhost:8080/api/register/carDealer`, carDealer,{observe: 'response',withCredentials: true});
  }

  updateCarDealer(id: number, value: any): Observable<Object> {
    return this.http.put(`${this.baseUrl}/update`, value);
  }

  deleteCarDealer(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
  }
}
