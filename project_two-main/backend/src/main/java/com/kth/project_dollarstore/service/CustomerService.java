package com.kth.project_dollarstore.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.kth.project_dollarstore.exception.EmailAlreadyTakenException;
import com.kth.project_dollarstore.exception.WeakPasswordException;
import com.kth.project_dollarstore.model.Customer;
import com.kth.project_dollarstore.model.DeleteReason;
import com.kth.project_dollarstore.model.PasswordResetToken;
import com.kth.project_dollarstore.model.StrongPassword;
import com.kth.project_dollarstore.repository.CustomerRepository;
import com.kth.project_dollarstore.repository.DeleteReasonRepository;
import com.kth.project_dollarstore.repository.PasswordResetTokenRepository;

import jakarta.transaction.Transactional;

/**
 * Service class for managing customer operations such as registration, login, and password resets, account deletion.
 * Handles customer-related actions and interactions with the repository and email services.
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private DeleteReasonRepository deleteReasonRepository;

   /**
     * Registers a new customer after validating email uniqueness and password strength.
     * Hashes the password before saving the customer to the repository.
     * 
     * @param customer The customer to be registered.
     * @return A message indicating the result of the registration process.
     */
    public String addCustomer(Customer customer) {  
        Optional<Customer> existingCustomer = customerRepository.findByEmail(customer.getEmail());
        if (existingCustomer.isPresent()) {
            throw new EmailAlreadyTakenException("Email already taken");
        }
        if (!StrongPassword.isPasswordValid(customer.getPassword())) {
            throw new WeakPasswordException("Weak password");
        }
        encryptPassword(customer);
        customerRepository.save(customer);
        return "Customer registered successfully";
    }
    
    /**
     * Encrypts the customer's password by the use of BCrypt.
     * 
     * @param customer The customer whose password is to be encrypted.
     */
    private void encryptPassword(Customer customer) {
        String encryptedPassword = BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt());
        customer.setPassword(encryptedPassword);
    }

    /**
     * Retunrs all customers from the repository.
     * 
     * @return A list of all customers.
     */
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Retrieves a customer by their ID.
     * 
     * @param id The ID of the customer to retrieve.
     * @return An Optional containing the customer if found, otherwise empty.
     */
    public Optional<Customer> getCustomerById(Integer id) {
        return customerRepository.findById(id);
    }

    /**
     * Deletes a customer by their ID.
     * 
     * @param id The ID of the customer to be deleted.
     */
    public void deleteCustomerById(Integer id) {
        customerRepository.deleteById(id);
    }

    /**
     * Updates a customer by specified by their ID with new details.
     * 
     * @param id The ID of the customer to be updated.
     * @param customer The customer detals to be updated.
     * @return An Optional containing the updated customer if found, or empty.
     */
    public Optional<Customer> updateCustomerById(Integer id, Customer customer) {
        Optional<Customer> updatingCustomer = customerRepository.findById(id);
        if(updatingCustomer.isPresent()){
            Customer n_cs = updatingCustomer.get();
            if(!(customer.getAddress() == null)){
                n_cs.setAddress(customer.getAddress());
            }
            if(!(customer.getAge() == null)){
                n_cs.setAge(customer.getAge());
            }
            if(!(customer.getName() == null)){
                n_cs.setName(customer.getName());
            }
            if(!(customer.getDob() == null)){
                n_cs.setDob(customer.getDob());
            }
            if(!(customer.getEmail() == null)){
                n_cs.setEmail(customer.getEmail());
            }
            if(!(customer.getPhoneNumber() == null)){
                n_cs.setPhoneNumber(customer.getPhoneNumber());
            }
            if(!(customer.getPostalCode() == null)){
                n_cs.setPostalCode(customer.getPostalCode());
            }
            if (customer.getPassword() != null) {
                if (!StrongPassword.isPasswordValid(customer.getPassword())) {
                    throw new WeakPasswordException("Weak password");
                }
                n_cs.setPassword(customer.getPassword());
                encryptPassword(n_cs);
            }
            customerRepository.save(n_cs);

        }
        return updatingCustomer;
    }
    
    /**
     * Returns a customer by their email address.
     * 
     * @param email The email address of the customer to return.
     * @return An Optional containing the customer if found, or empty.
     */
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }  

    /**
     * Authenticates a customer by validating their email and password credentials.
     * 
     * @param email The email of the customer.
     * @param password The password of the customer.
     * @return The customer ID if authentication is successful, otherwise an error message.
     */
    public String login(String email, String password) {
        Optional<Customer> customerOptional = getCustomerByEmail(email);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            if (BCrypt.checkpw(password, customer.getPassword()))  {   
                return String.valueOf(customer.getId());
            } else {
                return "Invalid credentials";
            }
        }
        return "User not found";
    }

    /**
     * Creates a password reset token for the specified customer and saves it to the repository.
     * 
     * @param customer The customer for whom the password reset token is created.
     * @param token The password reset token.
     */
    public void createPasswordResetTokenForCustomer(Customer customer, String token) {
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setToken(token);
        myToken.setCustomer(customer);
        myToken.setExpiryDate(new Date(System.currentTimeMillis() + 600000)); // 10 minutes expiry, update here if needed
        tokenRepository.save(myToken);
    }

    /**
     * Asynchronously sends a password reset email to the specified email address.
     * 
     * @param email The email address to send the password-reset email to.
     */
    @Async // Background task
    public void sendPasswordResetEmail(String email) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            String token = UUID.randomUUID().toString();
            
            createPasswordResetTokenForCustomer(customer, token);
            
            try {
                emailService.sendPasswordResetEmail(email, token);
            } catch (Exception e) {
                throw new RuntimeException("Failed", e);
            }
        } else {
            throw new IllegalArgumentException("User" + email + " not found.");
        }
    }
    
    /**
     * Finds a customer by their password reset token if the token is not expired.
     * 
     * @param token The password reset token.
     * @return An Optional containing the customer if the token is valid, or empty.
     */
    public Optional<Customer> findCustomerByResetToken(String token) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);

        if (resetToken != null && resetToken.getExpiryDate().after(new Date())) {
            return Optional.of(resetToken.getCustomer());
        } else {
            return Optional.empty();
        }
    }

    /**
     * Updates the password for the given customer and deletes the associated password-reset token.
     * 
     * @param customer The customer whose password to update.
     * @param newPassword The new password.
     */
    @Transactional
    public void updatePassword(Customer customer, String newPassword) {
        if (!StrongPassword.isPasswordValid(newPassword)) { 
            throw new WeakPasswordException("Weak password");
        }
        try {
            customer.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            customerRepository.save(customer);
            tokenRepository.deleteByCustomer(customer);
        } catch (Exception e) {
            throw new RuntimeException("Error updating password", e);
        }
    }
    
    /**
     * Saves a delete reason for a customer.
     * 
     * @param deleteReason The delete reason to be saved.
     * @return The saved delete reason.
     */
    public DeleteReason saveDeleteReason(DeleteReason deleteReason) {
        deleteReason.setCreatedAt(LocalDateTime.now());
        return deleteReasonRepository.save(deleteReason);
    }
}