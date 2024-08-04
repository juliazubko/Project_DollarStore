package com.kth.project_dollarstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kth.project_dollarstore.model.Customer;


/**
 * Repository interface for handling {@link Customer} entities.
 * Extends {@link JpaRepository} to enable standard CRUD operations and additional query capabilities.
 * 
 * This interface allows a method to find a customer by their email address.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Optional<Customer> findByEmail(String email);   
}