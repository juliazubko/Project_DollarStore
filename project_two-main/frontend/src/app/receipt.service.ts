import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Receipt {
  id: number;
  butik: string;
  datum: Date;
  tid: string;
  kvittonummer: string;
  totalPrice: number;
  receiptImageUrl: string;
  notes: string;
}

@Injectable({
  providedIn: 'root'
})
export class ReceiptService {
  private apiUrl = 'http://localhost:8080/api/v1/customers';

  constructor(private http: HttpClient) { }

  getReceiptsByCustomerId(customerId: number): Observable<Receipt[]> {
    return this.http.get<Receipt[]>(`${this.apiUrl}/${customerId}/receipts`);
  }

  getReceiptImage(customerId: number, receiptId: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${customerId}/receipts/image/${receiptId}`, { responseType: 'blob' });
  }

  getReceiptById(customerId: number, receiptId: number): Observable<Receipt> {
    return this.http.get<Receipt>(`${this.apiUrl}/${customerId}/receipts/${receiptId}`);
  }

  deleteReceipt(customerId: number, receiptId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${customerId}/receipts/${receiptId}`);
  }

  downloadReceipt(customerId: number, receiptId: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${customerId}/receipts/${receiptId}/download`, { responseType: 'blob' });
  }

  uploadReceipt(customerId: number, formData: FormData): Observable<any> {
    return this.http.post(`${this.apiUrl}/${customerId}/upload`, formData);
  }

  updateReceipt(customerId: number, receiptId: number, receipt: Receipt): Observable<Receipt> {
    return this.http.put<Receipt>(`${this.apiUrl}/${customerId}/receipts/${receiptId}/edit`, receipt);
  }
}
