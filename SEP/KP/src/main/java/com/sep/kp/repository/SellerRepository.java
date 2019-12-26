package com.sep.kp.repository;


import com.sep.kp.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {

    Seller findByClientAndPaymentMethodName(String client, String paymentMethod);
    List<Seller> findByClient(String client);
    Seller findByClientId(String client);
}
