package com.sep.paypal;

import com.sep.paypal.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findTransactionByHashedTransactionId(String hashedId);
}
