package com.kth.project_dollarstore.controller;

import com.kth.project_dollarstore.model.ReceiptMetaData;
import com.kth.project_dollarstore.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that handles recipts.
 * 
 * Provides an endpoint to retrieve receipt images by ID.
 * Returns the image in JPEG format or a 404 status if not found.
 */
@RestController
@RequestMapping("api/v1/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    /**
     * Gets the the image of a receipt by its ID.
     *
     * Returns the image as a byte array with appropriate headers if found or 404 if not found
     *
     * @param id The ID of the receipt.
     * @return ResponseEntity with the receipt image or 404 status if not found.
     */
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getReceiptImage(@PathVariable Long id) {
        ReceiptMetaData receipt = receiptService.getReceiptById(id);

        if (receipt != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(receipt.getReceiptImage().length);
            return new ResponseEntity<>(receipt.getReceiptImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}