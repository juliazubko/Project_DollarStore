package com.kth.project_dollarstore.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kth.project_dollarstore.dto.ReceiptMetaDataDto;
import com.kth.project_dollarstore.model.ReceiptMetaData;
import com.kth.project_dollarstore.repository.ReceiptRepository;

/**
 * Service class for managing receipt operations.
 * Provides methods for saving, updating, retrieving, and deleting receipts.
 */
@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    /**
     * Saves a new receipt with the given details;
     *
     * @param file The receipt image file.
     * @param butik The store name.
     * @param datum The date of when receipt was made.
     * @param tid The time of when receipt was made.
     * @param kvittonummer The receipt number.
     * @param totalPrice The total price.
     * @param customerId The ID of the customer associated with the recept.
     * @return The ReceiptMetaData.
     * @throws IOException Handles error while processing the file if it occurs.
     */
    public ReceiptMetaData saveReceipt(MultipartFile file, String butik, Date datum, String tid, String kvittonummer, Float totalPrice, Integer customerId) throws IOException {
        ReceiptMetaData receipt = new ReceiptMetaData();
        receipt.setButik(butik);
        receipt.setDatum(datum);
        receipt.setTid(tid);
        receipt.setKvittonummer(kvittonummer);
        receipt.setTotalPrice(totalPrice);
        receipt.setReceiptImage(file.getBytes());
        receipt.setCustomerId(customerId);
        receipt.setNotes("");
        return receiptRepository.save(receipt);
    }

    /**
     * Retrieves a list of receipt Data Transfer Objects for a specific customer.
     *
     * @param customerId The ID of the associated customer.
     * @return A list of ReceiptMetaData Dto objects.
     */
    public List<ReceiptMetaDataDto> getReceiptsByCustomerId(Integer customerId) {
        return receiptRepository.findReceiptDtosByCustomerId(customerId);
    }

    /**
     * Retrieves a receipt by its ID.
     *
     * @param id The ID of the receipt to retrieve.
     * @return The ReceiptMetaData object if found or null if not found.
     */
    public ReceiptMetaData getReceiptById(Long id) {
        return receiptRepository.findById(id).orElse(null);
    }  

    /**
     * Updates an existing receipt.
     *
     * @param receiptId The ID of the receipt to update.
     * @param receipt The ReceiptMetaData object with the updated details.
     * @return An Optional containing the updated ReceiptMetaData if successful, or empty if not found.
     */
    public Optional<ReceiptMetaData> updateReceiptById(Long receiptId, ReceiptMetaData receipt) {
        Optional<ReceiptMetaData> updatingReceipt = receiptRepository.findById(receiptId);
        if (updatingReceipt.isPresent()) {
            ReceiptMetaData n_rct = updatingReceipt.get();
            if (receipt.getButik() != null) {
                n_rct.setButik(receipt.getButik());
            }
            if (receipt.getDatum() != null) {
                n_rct.setDatum(receipt.getDatum());
            }
            if (receipt.getTid() != null) {
                n_rct.setTid(receipt.getTid());
            }
            if (receipt.getKvittonummer() != null) {
                n_rct.setKvittonummer(receipt.getKvittonummer());
            }
            if (receipt.getTotalPrice() != null) {
                n_rct.setTotalPrice(receipt.getTotalPrice());
            }
            if (receipt.getNotes() != null) {
                n_rct.setNotes(receipt.getNotes());
            }
            receiptRepository.save(n_rct);
        }
        return updatingReceipt;
    }

    /**
     * Deletes a receipt by the given customer.
     *
     * @param customerId The ID of the customer associated with the receipt.
     * @param receiptId The ID of the receipt to delete.
     * @return A ResponseEntity indicating success or failure.
     */
    public ResponseEntity<Void> deleteReceipt(Integer customerId, Long receiptId) {
        Optional<ReceiptMetaData> receipt = receiptRepository.findById(receiptId);
        if (receipt.isPresent() && receipt.get().getCustomerId().equals(customerId)) {
            receiptRepository.deleteById(receiptId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a receipt by the specified customer.
     *
     * @param customerId The ID of the customer.
     * @param receiptId The ID of the receipt.
     * @return An Optional containing the ReceiptMetaData if found and matches the customer, or an empty not found.
     */
    public Optional<ReceiptMetaData> getReceiptForCustomer(Integer customerId, Long receiptId) {
        Optional<ReceiptMetaData> receipt = receiptRepository.findById(receiptId);
        if (receipt.isPresent() && receipt.get().getCustomerId().equals(customerId)) {
            return receipt;
        }
        return Optional.empty();
    }
}