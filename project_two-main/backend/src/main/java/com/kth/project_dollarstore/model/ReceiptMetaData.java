package com.kth.project_dollarstore.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents receipt metadata including purchase details and receipt image.
 * Stores information such as store name, date, time, receipt number, and total price.
 * Provides a method to generate a URL for the receipt image.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReceiptMetaData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "receipt_id_sequence")
    @SequenceGenerator(name = "receipt_id_sequence", sequenceName = "receipt_id_sequence", allocationSize = 1)
    private Long id;

    /**
     * Name of the store where the purchase was made.
     */
    private String butik;

    /**
     * Date of the purchase.
     */
    private Date datum;

    /**
     * Time of the purchase.
     */
    private String tid;

    /**
     * Receipt number.
     */
    private String kvittonummer;

    /**
     * Total price of the purchase.
     */
    private Float totalPrice;

    /**
     * ID of the customer associated with this receipt.
     */
    private Integer customerId;

    /**
     * Additional notes related to the receipt.
     */
    private String notes;

    /**
     * Image of the receipt stored as a byte array.
     * This field is marked as transient to prevent serialization and deserialization of the image data.
     */
    @Lob
    @Column(name = "receipt_image")
    @JsonIgnore
    private byte[] receiptImage;

    /**
     * Generates a URL to access the receipt image.
     * 
     * @return A string URL to access the receipt image.
     */
    public String getReceiptImageUrl() {
        return "/api/v1/customers/" + this.customerId + "/receipts/image/" + this.id;
    }

    @Override
    public String toString() {
        return "ReceiptMetaData [id=" + id + ", butik=" + butik + ", datum=" + datum + ", tid=" + tid + ", kvittonummer=" + kvittonummer + ", totalPrice=" + totalPrice + "]";
    }
}