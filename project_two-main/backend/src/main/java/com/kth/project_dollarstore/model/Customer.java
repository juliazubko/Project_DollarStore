package com.kth.project_dollarstore.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The modell for a customer user in the system.
 * 
 * This entity includes details all details needed for a user
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {

    @Id
    @SequenceGenerator(
        name = "customer_id_sequence",
        sequenceName = "customer_id_sequence", 
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "customer_id_sequence"
    )
    private Integer id;
    private String name;
    private LocalDate dob;
    private Integer age;

    @Column(unique = true)
    private String email;
    
    private String address;
    private String postalCode;
    private Integer phoneNumber;
    private String password; 

    public Customer(String name, LocalDate dob, String email, String password) {
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.password = password;
        this.age = calculateAge(dob);
    }
    
    /**
     * Sets the age of the customer before its stored or updated.
     * The method is called automatically by Java Persistent API
     */
    @PrePersist
    @PreUpdate
    public void prePersist() {
        this.age = calculateAge(this.dob);
    }

    /**
     * Calculates the age based on the provided date of birth.
     * 
     * @param dob The date of birth used to compute the age.
     * @return The calculated age in years.
     * @throws IllegalArgumentException if the date of birth is null.
     */
    private int calculateAge(LocalDate dob) {
        if (dob == null) {
            throw new IllegalArgumentException("Date of Birth is not set.");
        }
        LocalDate today = LocalDate.now();
        return Period.between(dob, today).getYears();
    }

    /**
     * A simple toString that representation of the Customer object.
     * 
     * @return A string that includes the customer's details such as ID, name, email and more.
     */
    @Override
    public String toString() {
        return "Customer [id=" + id + ", name=" + name + ", dob=" + dob + ", age=" + age + ", email=" + email
                + ", address=" + address + ", postalCode=" + postalCode + ", phoneNumber=" + phoneNumber + "]";
    }
}
