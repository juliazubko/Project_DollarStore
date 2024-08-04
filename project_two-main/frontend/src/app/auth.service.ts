import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(
    private router: Router,
    private storageService: StorageService
  ) {}

  login(customerId: string) {
    this.storageService.setItem('customerId', customerId);
  }

  logout() {
    this.storageService.removeItem('customerId');
    this.router.navigate(['/loginchoose']);
  }

  isLoggedIn(): boolean {
    return this.storageService.getItem('customerId') !== null;
  }
}
