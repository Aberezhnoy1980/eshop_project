import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from "@angular/router";
import {AuthService} from "../../service/auth.service";

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {

  isGalleryPage: boolean = false;
  isCartPage: boolean = false;
  isOrderPage: boolean = false;
  isLoginPage: boolean = false;

  constructor(private router: Router, private authService: AuthService) {
  }

  ngOnInit(): void {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.isGalleryPage = event.url === '/' || event.url === '/product';
        this.isCartPage = event.url === '/cart';
        this.isOrderPage = event.url === '/order';
        this.isLoginPage = event.url === '/login';
      }
    })
  }

  public isAuthenticated(): boolean {
    return !!this.authService.currentUser;
  }

  logout() {
    return this.authService.logout();
  }
}
