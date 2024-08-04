import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-dollar-header',
  templateUrl: './dollar-header.component.html',
  styleUrl: './dollar-header.component.css'
})
export class DollarHeaderComponent {
  menuOpen = false;  
  constructor(public router: Router, public authService: AuthService) { }

  logout() {
    this.authService.logout();
  } 

  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
    const menu = document.getElementById('bottom-bar');
    if (menu) {
      menu.style.left = this.menuOpen ? '0px' : '-100%';
    }
  } 
}