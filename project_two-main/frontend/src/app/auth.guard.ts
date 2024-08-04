import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    if (this.authService.isLoggedIn()) {
      console.log('Authorized');
      return true;
    } else {
      console.log('Not authorized, chagning');
      this.router.navigate(['/loginchoose']).then(() => {
        console.log('Page change worked');
      }).catch(err => {
        console.error('Didnt change page', err);
      });
      return false;
    }
  }
}
