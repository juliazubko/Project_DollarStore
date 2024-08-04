import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Customer {
  id: number;
  name: string;
  email: string;
  address: string;
  postalCode: string;
  phoneNumber: number;
  password?: string; 
  dob: string;
  phonecode: string;
  age: number;
}

export interface DeleteReason {
  reason: string;
  otherReason?: string;
}

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private apiUrl = 'http://localhost:8080/api/v1/customers';

  constructor(private http: HttpClient) { }

  // funkar med backend
  login(email: string, password: string): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/login`, { email, password }, { responseType: 'text' as 'json' });
  }

  getCustomerById(id: number): Observable<Customer> {
    return this.http.get<Customer>(`${this.apiUrl}/${id}`);
  }

  updateCustomer(id: number, customer: Customer): Observable<Customer> {
    return this.http.put<Customer>(`${this.apiUrl}/${id}`, customer);
  }

  deleteCustomer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  registerCustomer(customer: { name: string; dob: string; email: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, customer);
  }

  submitDeleteReason(deleteReason: DeleteReason): Observable<any> {
    return this.http.post(`${this.apiUrl}/delete-reason`, deleteReason);
  }

  requestPasswordReset(email: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/reset-password`, { email });
  }

  resetPassword(token: string, newPassword: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/change-password`, { token, password: newPassword });
  }

}
