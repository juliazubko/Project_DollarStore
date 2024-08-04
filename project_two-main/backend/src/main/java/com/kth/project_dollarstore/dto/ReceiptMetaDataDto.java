package com.kth.project_dollarstore.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transer object for receipt metadata.
 * 
 * Holds details about a receipt including ID, store, date, time, number, total price, notes, and image URL.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptMetaDataDto {
    private Long id;
    private String butik;
    private Date datum;
    private String tid;
    private String kvittonummer;
    private Float totalPrice;
    private String notes;
    private String receiptImageUrl;    
}
