import { MybookingsComponent } from './components/user/mybookings/mybookings.component';
import { BookingsComponent } from './components/user/bookings/bookings.component';
import { CookieServices } from './services/cookie.service';
import { Routes } from '@angular/router';
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/auth/login/login.component'; // Update with your actual Login component path
import { SignupComponent } from './components/auth/signup/signup.component'; // Update with your actual Signup component path
import { UserSignupComponent } from './components/auth/user-signup/user-signup.component';
import { CardealerSignupComponent } from './components/auth/cardealer-signup/cardealer-signup.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AccountsComponent } from './components/accounts/accounts.component';
import { AddcarComponent } from './components/cardealer/addcar/addcar.component';
import { MycarsComponent } from './components/cardealer/mycars/mycars.component';
import { CarsComponent } from './components/user/cars/cars.component';
import { MyordersComponent } from './components/cardealer/myorders/myorders.component';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router,private cookie:CookieServices) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const bypassRoutes = ['signup', 'userSignup', 'carDealerSignup'];
      if (bypassRoutes.includes(next.url[0].path) || this.cookie.checkCookie('user')) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: '', component: HomeComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard]},
  { path: 'userSignup', component: UserSignupComponent},
  { path: 'carDealerSignup', component: CardealerSignupComponent },
  { path: 'accounts', component: AccountsComponent, canActivate: [AuthGuard] },
  { path: 'addCar', component: AddcarComponent, canActivate: [AuthGuard] },
  { path: 'mycars', component: MycarsComponent, canActivate: [AuthGuard] },
  { path: 'cars', component: CarsComponent, canActivate: [AuthGuard] },
  { path: 'bookings', component: BookingsComponent, canActivate: [AuthGuard] },
  { path: 'bookings/:slug', component: BookingsComponent, canActivate: [AuthGuard] },
  { path: 'mybookings', component: MybookingsComponent, canActivate: [AuthGuard] },
  { path: 'myorders', component: MyordersComponent, canActivate: [AuthGuard] },
];
