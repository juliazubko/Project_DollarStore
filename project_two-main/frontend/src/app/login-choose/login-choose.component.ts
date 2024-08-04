import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CustomerService } from '../customer.service';
import { StorageService } from '../storage.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login-choose',
  templateUrl: './login-choose.component.html',
  styleUrls: ['./login-choose.component.css']
})
export class LoginChooseComponent {
  email: string = '';
  password: string = '';
  showUserNotFound: boolean = false;
  userNotFoundMessage: string = '';
  isSubmitting: boolean = false;

  constructor(
    private customerService: CustomerService, // från vår customer.service.ts, rätt request api tjänst
    private router: Router,
    private storageService: StorageService,
    private authService: AuthService
  ) {}

  onSubmit() {
    this.isSubmitting = true; // disable login btn once clicked

    this.customerService.login(this.email, this.password).subscribe(
      response => {
        if (/^\d+$/.test(response)) {
          this.authService.login(response);
          this.router.navigate(['/account']);
        } else {
          this.showUserNotFound = true;
          this.userNotFoundMessage = `DollarStore: ${response}`;
        }
        setTimeout(() => {
          this.isSubmitting = false;  // re-enable login btn after 5 sec
          console.log('5 sec after click. Button now enabled.');
        }, 5000); // adjust delay here
      },
      error => {
        console.error('Error:', error);
        this.isSubmitting = false; // re-enable login btn if returned login error
        console.log('Error occurred. Button now enabled.');
      }
    );
  }

  onDismiss() {
    this.showUserNotFound = false;
    this.email = '';
    this.password = '';
  }

  register() {
    this.router.navigate(['/registrera']);
  }
}
