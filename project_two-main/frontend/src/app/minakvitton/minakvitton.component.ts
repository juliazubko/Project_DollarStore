import { Component, OnInit } from '@angular/core';
import { ReceiptService, Receipt } from '../receipt.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { HttpClient } from '@angular/common/http';
import { saveAs } from 'file-saver';
import { StorageService } from '../storage.service';

@Component({
  selector: 'app-minakvitton',
  templateUrl: './minakvitton.component.html',
  styleUrls: ['./minakvitton.component.css']
})


export class MinakvittonComponent implements OnInit {
  receipts: Receipt[] = [];
  receiptImages: { [key: number]: SafeUrl } = {};
  showConfirmDialog = false;
  currentReceipt?: Receipt;
  isEditing: { [key: number]: boolean } = {};
  selectedFile?: File;
  newReceipt: Partial<Receipt> = {};
  selectedDate?: Date;
  sortDirection: 'asc' | 'desc' = 'asc';
  currentImageUrl: SafeUrl | null = null;
  showImageModal = false;


currentPage = 1;
pageSize = 10;

  constructor(
    private receiptService: ReceiptService,
    private sanitizer: DomSanitizer,
    private http: HttpClient,
    private storageService: StorageService
  ) { }

  ngOnInit(): void {
    const customerId = this.storageService.getItem('customerId');
    if(customerId !== null){
      this.loadReceipts(parseInt(customerId,10));
    } else {
      console.error("No customer id found");
    }
  }

  loadReceipts(customerId: number): void {
    this.receiptService.getReceiptsByCustomerId(customerId).subscribe((receipts: Receipt[]) => {
      // console.log(receipts); // debugging purp
      this.receipts = receipts;
      receipts.forEach(receipt => {
        this.loadReceiptImage(customerId, receipt.id);
      });
    });
  }

  loadReceiptImage(customerId: number, receiptId: number): void {
    this.receiptService.getReceiptImage(customerId, receiptId).subscribe(imageBlob => {
      const url = URL.createObjectURL(imageBlob);
      this.receiptImages[receiptId] = this.sanitizer.bypassSecurityTrustUrl(url);
    });
  }

  onEditButtonClick(receipt : Receipt) {
    console.log('Edit button clicked for receipt:', receipt);
  }

  toggleEdit(receipt: Receipt): void {
    const receiptId = receipt.id;
    if (this.isEditing[receiptId]) {
      const customerId = this.storageService.getItem('customerId');
      if (customerId) {
        this.receiptService.updateReceipt(parseInt(customerId, 10), receiptId, receipt).subscribe(response => {
          console.log('Receipt updated:', response);
          this.isEditing[receiptId] = false;
        }, error => {
          console.error('Error:', error);
        });
      }
    } else {
      this.isEditing[receiptId] = true;
    }
  }


  onRemoveButtonClick(receipt: Receipt): void {
    console.log('Remove button clicked for receipt ID:', receipt);
    this.showConfirmDialog = true;
    this.currentReceipt = receipt;
  }
  removeReceipt(receipt: Receipt): void {
    const customerId = this.storageService.getItem('customerId');
    if (customerId !== null) {
      this.receiptService.deleteReceipt(parseInt(customerId, 10), receipt.id).subscribe({
        next: () => {
          console.log('Receipt removed successfully');
          this.receipts = this.receipts.filter(r => r.id !== receipt.id);
          delete this.receiptImages[receipt.id];
          this.showConfirmDialog = false;
        },
        error: (error) => console.error('Error removing receipt:', error)
      });
    } else {
      console.error("No customer id found");
    }
  }


  cancelRemove(): void {
    console.log('Removal cancelled');
    this.showConfirmDialog = false;
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }



  downloadReceipt(receiptId: number): void {
    const customerId = this.storageService.getItem('customerId');
    if (customerId !== null) {
      this.receiptService.downloadReceipt(parseInt(customerId, 10), receiptId).subscribe(response => {
        const blob = new Blob([response], { type: 'image/jpeg' });
        saveAs(blob, `receipt_${receiptId}.jpg`);
      }, error => {
        console.error('Error downloading receipt:', error);
      });
    } else {
      console.error("No customer id found");
    }
  }

  onDateSelected(event: any): void {
    this.selectedDate = event.target.valueAsDate;
    if (this.selectedDate) {
      const customerId = localStorage.getItem('customerId');
      if (customerId !== null) {
        this.loadReceipts(parseInt(customerId, 10));
      }
    }
  }

  uploadReceipt(): void {
    if (!this.selectedFile) {
      console.error("No file selected");
      return;
    }

    const customerId = this.storageService.getItem('customerId');
    if (customerId === null) {
      console.error("No customer id found");
      return;
    }

    const dateString = this.newReceipt.datum as unknown as string;
    const datum = new Date(dateString);

    const formData = new FormData();
    formData.append('file', this.selectedFile);
    formData.append('butik', this.newReceipt.butik!);
    formData.append('datum', datum.toISOString());
    formData.append('tid', this.newReceipt.tid!);
    formData.append('kvittonummer', this.newReceipt.kvittonummer!);
    formData.append('total', this.newReceipt.totalPrice!.toString());

    this.receiptService.uploadReceipt(parseInt(customerId, 10), formData).subscribe(response => {
      console.log('Receipt uploaded:', response);
      this.loadReceipts(parseInt(customerId, 10));
    }, error => {
      console.error('Error uploading receipt:', error);
    });
  }



  toggleSort(): void {
    if (this.sortDirection === 'asc') {
      this.receipts.sort((a, b) => new Date(a.datum).getTime() - new Date(b.datum).getTime());
      this.sortDirection = 'desc';
    } else {
      this.receipts.sort((a, b) => new Date(b.datum).getTime() - new Date(a.datum).getTime());
      this.sortDirection = 'asc';
    }
  }

  onPageChange(page: number): void {
    this.currentPage = page;
  }

  openImageModal(imageUrl: SafeUrl): void {
    console.log('Modal should open with image:', imageUrl);
    this.currentImageUrl = imageUrl;
    this.showImageModal = true;
  }
}
