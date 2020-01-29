package com.sep.kp.repository;

import com.sep.kp.model.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {
  PaymentRequest findByMerchantOrderId (Long name);

}
