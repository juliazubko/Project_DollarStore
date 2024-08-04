import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CustomerService } from '../customer.service';

@Component({
  selector: 'app-registrera',
  templateUrl: './registrera.component.html',
  styleUrls: ['./registrera.component.css']
})
export class RegistreraComponent {
  name: string = '';
  dob: string = ''; // Use string for dob initially
  email: string = '';
  password: string = '';
  confirmPassword: string = '';
  isPasswordMatched: boolean = true; 
  isStrongPassword: boolean = true;
  isSubmitting: boolean = false;

  constructor(private customerService: CustomerService, private router: Router) {}

  register() {
    this.isPasswordMatched = this.password === this.confirmPassword;
    this.isStrongPassword = this.isPasswordRequirementsMet(this.password);

    if (!this.isPasswordMatched || !this.isStrongPassword || this.isSubmitting || !this.dob) {
      return;
    }

    this.isSubmitting = true;
    console.log('Submit button disabled');
    
    this.customerService.registerCustomer({
      name: this.name,
      dob: this.dob, // Send the string format (YYYY-MM-DD) directly
      email: this.email,
      password: this.password
    })
    .subscribe(response => {
      alert('Registrering lyckades!');
      this.router.navigate(['/loginchoose']);
    }, error => {
      if (error.status === 409) {
        alert('Email redan tagen. Vänligen använd en annan e-postadress.');
      } else {
        console.error('Registrering misslyckades:', error);
        alert('Registrering misslyckades. Vänligen försök igen.');
      }
    })
    .add(() => {
      setTimeout(() => {
        this.isSubmitting = false;
        console.log('Submit button enabled');
      }, 3000);
    });
  }

  isPasswordRequirementsMet(password: string): boolean {
    const minLength = 8;
    const hasUpperCase = /[A-Z]/.test(password);
    const hasLowerCase = /[a-z]/.test(password);
    const hasDigit = /\d/.test(password);

    return password.length >= minLength && hasUpperCase && hasLowerCase && hasDigit;
  }
}
