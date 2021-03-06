package com.sep.kp.repository;

import com.sep.kp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    Transaction findTransactionByIdHashValue(String hashedId);
    Transaction findByMerchantOrderId(Long merchantOrderId);
    Transaction findTransactionByScientificCenterPurchaseId(Long id);
}
