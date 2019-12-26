package com.sep.bank.repository;


import com.sep.bank.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

   Card findByPanAndSecurityCodeAndCardholderNameAndValidTo(String pan, int securityCode, String cardholderName, Date validTo);
}
