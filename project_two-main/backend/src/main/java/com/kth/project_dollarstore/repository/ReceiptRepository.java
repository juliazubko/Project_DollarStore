package com.kth.project_dollarstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kth.project_dollarstore.dto.ReceiptMetaDataDto;
import com.kth.project_dollarstore.model.ReceiptMetaData;

/**
 * Repository for {@link ReceiptMetaData} entities.
 * The interface has a customized query to retrieve the {@link ReceiptMetaDataDto} by customer ID.
 */
@Repository
public interface ReceiptRepository extends JpaRepository<ReceiptMetaData, Long> {
    @Query("SELECT new com.kth.project_dollarstore.dto.ReceiptMetaDataDto(r.id, r.butik, r.datum, r.tid, r.kvittonummer, r.totalPrice, r.notes, CONCAT('/api/v1/customers/', :customerId, '/receipts/image/', r.id)) " +
           "FROM ReceiptMetaData r WHERE r.customerId = :customerId")
    List<ReceiptMetaDataDto> findReceiptDtosByCustomerId(@Param("customerId") Integer customerId);
}