package com.kth.project_dollarstore.model;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This entity contains a token used for resetting a users password.
 * It contains details such as the token itself, its expiry date,
 * and the customer associated with the token.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expiryDate;

    /**
     * The customer to which the token belongs wit this token.
     * This relationship links the token to the specific customer.
     */
    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;
}