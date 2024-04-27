import { UserService } from './../../services/user.service';
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import * as toastr from 'toastr';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { CookieServices } from '../../services/cookie.service';
import { HostListener } from '@angular/core';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink, DashboardComponent],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent {
  userName:any;
  isLoggedIn(): boolean {
    return this.cookie.checkCookie('user');
  }
  constructor(
    private userService: UserService,
    private cookie: CookieServices
    ) {}

  ngOnInit(): void {
    //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    //Add 'implements OnInit' to the class.
    this.userName= JSON.parse(this.cookie.getCookie('user')).email;
  }

  logout() {
    toastr.options.positionClass = 'toast-bottom-right';
    toastr.info('logout successfull');
    this.userService.logout();
  }
  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent) {
    const clickover = event.target as HTMLElement;
    const opened = (document.querySelector('.navbar-collapse') as HTMLElement)?.classList.contains('show');
    if (opened === true && !clickover.classList.contains('navbar-toggler')) {
      (document.querySelector('.navbar-toggler') as HTMLElement)?.click();
    }
  }



}
