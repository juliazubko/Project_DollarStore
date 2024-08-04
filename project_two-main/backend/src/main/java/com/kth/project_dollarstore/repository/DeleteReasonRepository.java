package com.kth.project_dollarstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kth.project_dollarstore.model.DeleteReason;

/**
 * Repository interface for handling {@link DeleteReason} entities.
 * Extends {@link JpaRepository} to provide basic CRUD operations for {@link DeleteReason}.
 */
@Repository
public interface DeleteReasonRepository extends JpaRepository<DeleteReason, Integer> {}
