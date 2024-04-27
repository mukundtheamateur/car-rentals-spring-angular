import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Bookings } from '../models/bookings';

@Injectable({
  providedIn: 'root'
})
export class BookingsService {
  private apiUrl = 'http://localhost:8080/api/bookings';

  constructor(private http: HttpClient) { }

  getAllBookings(): Observable<Bookings[]> {
    return this.http.get<Bookings[]>(this.apiUrl);
  }

  getBookingById(id: number): Observable<Bookings> {
    return this.http.get<Bookings>(`${this.apiUrl}/${id}`);
  }

  saveBooking(booking: any): Observable<Bookings> {
    return this.http.post<Bookings>(`${this.apiUrl}/create`, booking);
  }

  deleteBooking(id: number): Observable<string> {
    return this.http.delete<string>(`${this.apiUrl}/${id}`);
  }

  getBookingsByUserId(userId: number): Observable<Bookings[]> {
    return this.http.get<Bookings[]>(`${this.apiUrl}/user/${userId}`);
  }

  getBookingsByCarId(carId: number): Observable<Bookings[]> {
    return this.http.get<Bookings[]>(`${this.apiUrl}/car/${carId}`);
  }
  getBookingsByCarDealerId(carDealerId: number): Observable<Bookings[]> {
    return this.http.get<Bookings[]>(`${this.apiUrl}/carDealer/${carDealerId}`);
  }

  updateBooking(booking: any): Observable<Bookings> {
    return this.http.put<Bookings>(`${this.apiUrl}/update`, booking);
  }
}
