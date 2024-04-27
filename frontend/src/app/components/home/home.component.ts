import { CookieServices } from './../../services/cookie.service';
import { Router,RouterLink } from '@angular/router';
import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { DashboardComponent } from '../dashboard/dashboard.component';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NavbarComponent, RouterLink, DashboardComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  userId:any;
  constructor(private cookie:CookieServices, private router:Router){}

  // ngOnInit(): void {

  //   this.redirectTo();
  // }
  redirectTo(){
    let userId = JSON.parse(this.cookie.getCookie('user')).role.id;
    this.userId = userId;
    if(userId==1){
      this.router.navigate(['/cars']);
    }
    else if(userId==2){
      this.router.navigate(['/mycars']);
    }
    else{
      this.router.navigate(['/login']);
    }
  }

}
