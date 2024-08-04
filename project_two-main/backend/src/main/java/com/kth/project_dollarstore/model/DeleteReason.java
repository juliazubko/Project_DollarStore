package com.kth.project_dollarstore.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents feedback for a users account deletion reason.
 * This entity includes details about why an account was deleted,
 * including a predefined reason or other that the user specifies
 * in string format, and the date when the reason was created.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteReason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private Reason reason;

    private LocalDateTime createdAt;
    private String otherReason;
}