import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-confirm-deletion-dialog',
  templateUrl: './confirm-deletion-dialog.component.html',
  styleUrls: ['./confirm-deletion-dialog.component.css']
}) 

export class ConfirmDeletionDialogComponent {  
  @Input() receipt: any;   
  @Output() onConfirm = new EventEmitter<void>();
  @Output() onDismiss = new EventEmitter<void>();

  confirm() {
    this.onConfirm.emit();
  }

  dismiss() {
    this.onDismiss.emit();
  }
}
