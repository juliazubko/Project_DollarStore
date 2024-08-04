import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-user-not-found',
  templateUrl: './user-not-found.component.html',
  styleUrls: ['./user-not-found.component.css']
})
export class UserNotFoundComponent {
  @Input() message?: string;  
  @Output() dismissEvent = new EventEmitter<void>();
  
  dismiss() {
    this.dismissEvent.emit();  
  } 
}
