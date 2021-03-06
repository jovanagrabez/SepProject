package com.sep.kp.repository;


import com.sep.kp.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {

    Seller findByClientIdAndPaymentMethodsContaining(Long client, String paymentMethod);
    List<Seller> findByClientId(Long client);
    Seller findByClientId(String client);
    Seller findSellerByMagazineId(Long magazineId);
    Seller findSellerById(Long id);
    Seller findSellerByUserId(Long userId);
}
