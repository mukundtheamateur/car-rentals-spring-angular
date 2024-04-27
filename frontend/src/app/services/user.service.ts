import { EventEmitter, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { User } from '../models/user';
import { CookieServices } from './cookie.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080/api';


  constructor(private http: HttpClient, private cookieServices:CookieServices) { }


  logout(): void{

      localStorage.removeItem('jwt');
      this.cookieServices.deleteCookie('user');
    }
    login(user: any): Observable<any> {
        console.log("user is ",user)
        return this.http.post<any>(`${this.baseUrl}/login`, user, {observe: 'response',withCredentials: true});
      }
      // userChanged = new BehaviorSubject<User | undefined>(undefined);
      // login(user: any): Observable<any> {
      //   console.log("user is ",user)
      //   return this.http.post<any>(`${this.baseUrl}/login`, user, {observe: 'response',withCredentials: true}).pipe(
      //     tap((response: any) => {
      //       // Emit the userChanged event after a successful login
      //       this.userChanged.next(response.body);
      //     })
      //   );
      // }

      // logout(): void{
      //   localStorage.removeItem('jwt');
      //   this.cookieServices.deleteCookie('user');
      //   // Emit the userChanged event after a logout
      //   this.userChanged.next(undefined);
      // }

  loadUser():Observable<User>{
    return this.http.get<User>(`${this.baseUrl}/login/load/user`);
  }

  getAllUsers(): Observable<any> {
    return this.http.get(`${this.baseUrl}`);
  }

  getUserById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/id/${id}`);
  }

  createUser(user: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl}/register`, user,{observe: 'response',withCredentials: true});
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/id/${id}`, { responseType: 'text' });
  }

  getUserByEmail(email: string | null | undefined): Observable<any> {
    return this.http.get(`${this.baseUrl}/users/email/${email}`, {observe: 'response',withCredentials: true});
  }
  updateUser(id: number, userUpdates: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/users/update/${id}`, userUpdates, {observe: 'response', withCredentials: true});
  }

}
