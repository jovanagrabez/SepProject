package com.sep.kp.repository;

import com.sep.kp.model.PaymentData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDataRepository extends JpaRepository<PaymentData, Long> {
}
