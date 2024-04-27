import { Roles } from './../../models/roles';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { CookieServices } from '../../services/cookie.service';
import { User } from '../../models/user';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  user:any;
  userRole: string = "";
  constructor(private cookie: CookieServices) {}
  ngOnInit(): void {
    this.user = JSON.parse(this.cookie.getCookie('user') || '');

    if(this.user.role.id == 1){
      this.userRole = "user";
    }
    if(this.user.role.id == 2){
      this.userRole = "dealer";
    }
  }
}

