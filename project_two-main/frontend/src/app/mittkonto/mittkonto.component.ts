import { Component, OnInit } from '@angular/core';
import { CustomerService, Customer, DeleteReason } from '../customer.service';
import { Router } from '@angular/router';
import { StorageService } from '../storage.service';

@Component({
  selector: 'app-mittkonto',
  templateUrl: './mittkonto.component.html',
  styleUrls: ['./mittkonto.component.css']
})
export class MittkontoComponent implements OnInit {
  customer: Customer | null = null;

  isEditing: boolean = false;
  showConfirmDialog: boolean = false;
  showFeedbackDialog: boolean = false;
  showFeedbackConfirmationDialog: boolean = false;
  feedbackText: string = ''; // Bound to the text area
  selectedFeedback: string | null = null;
  showOtherTextArea: boolean = false;
  errorMessage: string = ''; // For displaying password errors

  constructor(
    private customerService: CustomerService,
    private router: Router,
    private storageService: StorageService
  ) { }

  ngOnInit(): void {
    const customerId = this.storageService.getItem('customerId');
    if (customerId !== null) {
      this.customerService.getCustomerById(parseInt(customerId, 10)).subscribe((data: Customer) => {
        this.customer = data;
        this.customer.password = '';
        this.customer.phonecode = "+46";
      });
    } else {
      console.error("No customer id found");
    }
  }

  toggleEdit(): void {
    if (this.isEditing && this.customer) {
      const customerId = this.storageService.getItem('customerId');
      if (customerId) {

          // Prepare payload and exclude unchanged fields
          const updatePayload = { ...this.customer };

          // If password field is empty, do not send it
          if (this.customer.password === '') {
            delete updatePayload.password;
          }


        this.customerService.updateCustomer(parseInt(customerId, 10), updatePayload).subscribe(response => {
          console.log('Customer updated:', response);
          this.isEditing = false;
          this.refreshPage();
        }, error => {
          console.error('Error:', error);
          if (error.status === 400 && error.error.message === 'Password does not meet the requirements') {
            this.errorMessage = 'Password is not strong enough. It must contain at least 8 characters, including an uppercase letter, a lowercase letter, and a digit.';
          } else {
            this.errorMessage = 'Password not strong enough.';
          }
        });
      }
    } else {
      this.isEditing = true;
    }
  }

  refreshPage(): void {
    window.location.reload();
  }

  confirmDeleteAccount(): void {
    console.log('Confirm delete account called');
    this.showConfirmDialog = true;
  }

  deleteAccount(): void {
    console.log('Delete account called');
    this.showConfirmDialog = false;
    const customerId = this.storageService.getItem('customerId');
    if (customerId) {
      this.customerService.deleteCustomer(parseInt(customerId, 10)).subscribe(() => {
        console.log('Account deleted successfully');
        this.storageService.removeItem('customerId');
        this.showFeedbackConfirmationDialog = true;
      }, error => {
        console.error('Error deleting account:', error);
      });
    }
  }

  submitFeedback(): void {
    if (!this.selectedFeedback && !this.feedbackText) {
      alert('Please select an option or specify "Other".');
      return;
    }

    const deleteReason: DeleteReason = {
      reason: this.selectedFeedback || 'OTHER',
      otherReason: this.selectedFeedback === 'OTHER' ? this.feedbackText : undefined
    };

    console.log('Submitting feedback:', deleteReason);

    this.customerService.submitDeleteReason(deleteReason).subscribe(
      response => {
        console.log('Feedback submitted successfully:', response);
        this.showFeedbackDialog = false;
        this.router.navigate(['/loginchoose']);
      },
      error => {
        console.error('Error submitting feedback:', error);
        alert('There was an error submitting your feedback. Please try again.');
      }
    );
  }

  cancelFeedback(): void {
    this.showFeedbackDialog = false;
    this.router.navigate(['/loginchoose']);
  }

  closeFeedbackDialog(): void {
    this.showFeedbackDialog = false;
    this.router.navigate(['/loginchoose']);
  }

  navigateToLogin(): void {
    this.router.navigate(['/loginchoose']);
  }

  onFeedbackChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.checked) {
      this.selectedFeedback = input.value;
      this.showOtherTextArea = input.value === 'OTHER';
      if (input.value !== 'OTHER') {
        this.feedbackText = ''; // Clear feedback text if not 'OTHER'
      }
    } else {
      if (input.value === 'OTHER') {
        this.showOtherTextArea = false;
        this.feedbackText = ''; // Clear feedback text when "Other" is deselected
      }
      if (this.selectedFeedback === input.value) {
        this.selectedFeedback = null;
      }
    }
  }
}
