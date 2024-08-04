package com.kth.project_dollarstore.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kth.project_dollarstore.dto.ReceiptMetaDataDto;
import com.kth.project_dollarstore.exception.EmailAlreadyTakenException;
import com.kth.project_dollarstore.exception.WeakPasswordException;
import com.kth.project_dollarstore.model.Customer;
import com.kth.project_dollarstore.model.DeleteReason;
import com.kth.project_dollarstore.model.ReceiptMetaData;
import com.kth.project_dollarstore.service.CustomerService;
import com.kth.project_dollarstore.service.ReceiptService;

/**
 * REST controller that manages customer details;
 * The class provides endpoints for registration, login, data retrieval, data modification, receipt management.
 */
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final ReceiptService receiptService;
    private static final SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
    
    public CustomerController(CustomerService customerService, ReceiptService receiptService) {
        this.customerService = customerService;
        this.receiptService = receiptService;
        
    }

    /**
     * Registers a new customer.
     *
     * @param customer The {@link Customer} the class object customer that wants to register.
     * @return ResponseEntity with HTTP status for success/error check.
     *         - Status 200 (OK) if registration is successful.
     *         - Status 409 (Conflict) if the email already exists.
     *         - Status 400 (Bad Request) if the password is weak.
     */
    @PostMapping("/register")
    public ResponseEntity<Void> addCustomer(@RequestBody Customer customer) {
        try {
            customerService.addCustomer(customer);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EmailAlreadyTakenException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (WeakPasswordException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Returns a list of all customers.
     *
     * @return List of Customer objects.
     */
    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    /**
     * Returns details of a specific customer by primarykey "ID".
     *
     * @param id The ID of the customer to retrieve.
     * @return Optional containing the Customer object if it exists, otherwise returns empty.
     */
    @SuppressWarnings("rawtypes")
    @GetMapping("/{customerId}")
    public Optional getCustomerById(@PathVariable("customerId") Integer id) {
        return customerService.getCustomerById(id);
    }

    /**
     * Deletes a specific customer by primarykry "ID".
     *
     * @param id The ID of the customer to delete.
     */
    @DeleteMapping("/{customerId}")
    public void deleteCustomerById(@PathVariable("customerId") Integer id) {
        customerService.deleteCustomerById(id);
    }

    /**
     * Updates the information of a specific customer.
     *            
     * @param id       The ID of the customer to update.
     * @param customer The updated Customer object.
     * @return Optional containing the updated Customer object if successful, empty otherwise.
     */
    @SuppressWarnings("rawtypes")
    @PutMapping("/{customerId}")
    public Optional updateCustomerById(@PathVariable("customerId") Integer id, @RequestBody Customer customer) {
        return customerService.updateCustomerById(id, customer);
    }

    /**
     * Verifies a customer based on email and password credentials
     *
     * @param customerDetails The customer object containing the email and password to login.
     * @return String indicating if the credentials was valid.
     */
    @PostMapping("/login")
    public String login(@RequestBody Customer customerDetails) {
        return customerService.login(customerDetails.getEmail(), customerDetails.getPassword());
    }  

    /**
     * Uploads a receipt for a customer.
     *
     * @param customerId   The ID of the customer to upload the receipt.
     * @param file         The receipt image file.
     * @param butik        The store name.
     * @param datum        The date of the receipt.
     * @param tid          The time when the receipt was logged.
     * @param kvittonummer The receipt number.
     * @param totalPrice   The total price.
     * @return ReceiptMetaData object that stores metadata of the saved receipt.
     * @throws IOException    Handles issue with the file
     * @throws ParseException Handles parsing issue.
     */
    @PostMapping("/{customerId}/upload")
    public ReceiptMetaData uploadReceipt(
        @PathVariable("customerId") Integer customerId,
        @RequestParam("file") MultipartFile file,
        @RequestParam("butik") String butik,
        @RequestParam("datum") String datum,
        @RequestParam("tid") String tid,
        @RequestParam("kvittonummer") String kvittonummer,
        @RequestParam("total") Float totalPrice
    ) throws IOException, ParseException {
        Date parsedDatum = date_format.parse(datum);
        return receiptService.saveReceipt(file, butik, parsedDatum, tid, kvittonummer, totalPrice, customerId);
    }

    /**
     * Returns a list of receipts for a specified customer ny primarykey "ID".
     *
     * @param customerId The ID of the customer to retrieve receipt from.
     * @return List of ReceiptMetaDataDto objects: the customer's receipts.
     */
    @GetMapping("/{customerId}/receipts")
    public List<ReceiptMetaDataDto> getCustomerReceipts(@PathVariable("customerId") Integer customerId) {
        return receiptService.getReceiptsByCustomerId(customerId);
    }

    /**
     * Updates a specified receipt for a customer.
     *
     * @param customerId  The ID of the customer whose receipt is being updated.
     * @param receiptId   The ID of the receipt to update.
     * @param receipt     The updated ReceiptMetaData object.
     * @return ResponseEntity containing the updated ReceiptMetaData object if successful, or a 404 status if not found.
     */
    @PutMapping("/{customerId}/receipts/{receiptId}/edit")
    public ResponseEntity<ReceiptMetaData> updateReceipt(
            @PathVariable("customerId") Integer customerId,
            @PathVariable("receiptId") Long receiptId,
            @RequestBody ReceiptMetaData receipt) {
        Optional<ReceiptMetaData> updatedReceipt = receiptService.updateReceiptById(receiptId, receipt);
        if (updatedReceipt.isPresent()) {
            return ResponseEntity.ok(updatedReceipt.get());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Gets an image of a specific receipt for a customer.
     *
     * @param customerId The ID of the customer whose receipt image is to be retrieved.
     * @param id         The ID of the receipt.
     * @return ResponseEntity containing the receipt image as a byte array if found, or a 404 status if not found.
     */
    @GetMapping("/{customerId}/receipts/image/{id}")
    public ResponseEntity<byte[]> getReceiptImage(@PathVariable("customerId") Integer customerId, @PathVariable Long id) {
        ReceiptMetaData receipt = receiptService.getReceiptById(id);

        if (receipt != null && receipt.getCustomerId().equals(customerId)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(receipt.getReceiptImage().length);
            return new ResponseEntity<>(receipt.getReceiptImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a specified receipt for a customer.
     *
     * @param customerId The ID of the customer whose receipt is to be deleted.
     * @param receiptId  The ID of the receipt to delete.
     * @return ResponseEntity indicating success or failure of the deletion.
     */    
    @DeleteMapping("/{customerId}/receipts/{receiptId}")
    public ResponseEntity<Void> deleteReceipt(@PathVariable("customerId") Integer customerId, @PathVariable("receiptId") Long receiptId) {
        return receiptService.deleteReceipt(customerId, receiptId);
    }

    /**
     * Downloads a specified receipt for a customer.
     *
     * @param customerId The ID of the customer whose receipt is to be downloaded.
     * @param receiptId  The ID of the receipt to download.
     * @return ResponseEntity containing the receipt image if found, or a 404 Not Found status if the receipt is not found.
     */
    @GetMapping("/{customerId}/receipts/{receiptId}/download")
    public ResponseEntity<byte[]> downloadReceipt(@PathVariable("customerId") Integer customerId, @PathVariable("receiptId") Long receiptId) {
        Optional<ReceiptMetaData> receipt = receiptService.getReceiptForCustomer(customerId, receiptId);

        if (receipt.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "receipt_" + receiptId + ".jpg");
            headers.setContentLength(receipt.get().getReceiptImage().length);
            return new ResponseEntity<>(receipt.get().getReceiptImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Sends a password reset email to the email address specified.
     *
     * @param request Map containing the email address.
     * @return ResponseEntity with a message indicating success or failure.
     */
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        try {
            customerService.sendPasswordResetEmail(email);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Password reset email sent.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to send password reset email.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Changes the password using a reset token code.
     *
     * @param request Map containing the reset token and new password.
     * @return ResponseEntity with a message indicating success, or an error message if the token is invalid or the password is weak.
     */
    @PostMapping("/change-password")
    public ResponseEntity<Map<String, String>> savePassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("password");
        
        Map<String, String> response = new HashMap<>();
        
        try {
            Optional<Customer> customerOptional = customerService.findCustomerByResetToken(token);
            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();
                customerService.updatePassword(customer, newPassword);
                response.put("message", "Password changed.");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Token invalid.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (IllegalStateException e) {
            response.put("error", "Weak password.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("error", "Error upon password change.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
      
    /**
     * Stores the reason for account deletion when submitted.
     *
     * @param deleteReason The reason for deletion.
     * @return ResponseEntity containing the saved DeleteReason object.
     */
    @PostMapping("/delete-reason")
    public ResponseEntity<DeleteReason> addDeleteReason(@RequestBody DeleteReason deleteReason) {
        DeleteReason savedReason = customerService.saveDeleteReason(deleteReason);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReason);
    }
}