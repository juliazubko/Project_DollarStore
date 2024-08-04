package com.kth.project_dollarstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kth.project_dollarstore.model.PasswordResetToken;
import com.kth.project_dollarstore.model.Customer;

/**
 * Repository for {@link PasswordResetToken} entities.
 * Provides methods to find tokens by their string value and delete tokens by the assosiated {@link Customer}.
 */
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    void deleteByCustomer(Customer customer);
}