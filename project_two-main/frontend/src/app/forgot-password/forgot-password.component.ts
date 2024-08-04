import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CustomerService } from '../customer.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
  email: string = '';
  token: string = '';
  newPassword: string = '';
  confirmPassword: string = '';
  passwordChanged: boolean = false;
  errorMessage: string = ''; // Single error message variable

  constructor(
    private customerService: CustomerService, 
    private router: Router
  ) {}

  onSubmit() {
    this.errorMessage = ''; // Clear previous error message

    this.customerService.requestPasswordReset(this.email)
      .subscribe(
        response => {
          console.log('Password reset email sent successfully');
          this.passwordChanged = true;
        },
        error => {
          console.error('Error sending password reset email:', error);
          this.errorMessage = 'Failed to send password reset email';
        }
      );
  }

  onPasswordReset() {
    this.errorMessage = '';

    if (this.newPassword !== this.confirmPassword) {
      this.errorMessage = 'Passwords do not match.';
      return;
    }

    this.customerService.resetPassword(this.token, this.newPassword)
      .subscribe(
        response => {
          console.log('Password changed successfully');
          this.router.navigate(['/loginchoose']);
        },

        // note, only one error message is handled simultaneousyly, should be good enough
        error => {
          console.error('Error changing password:', error);
          if (error.status === 400) {
            this.errorMessage = error.error.error || 'Weak password or token invalid.'; // Handle weak password or token invalid
          } else if (error.status === 404) {
            this.errorMessage = 'Invalid reset token.'; // Handle invalid token
          } else {
            this.errorMessage = 'An unexpected error occurred.'; // Handle unexpected errors
          }
        }
      );
  }
}
