package com.sep.bank.service.serviceImpl;

import com.sep.bank.model.Card;
import com.sep.bank.repository.CardRepository;
import com.sep.bank.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;


    @Override
    public Card find(String pan, int securityCode, String cardHolderName, Date validTo) {
        return cardRepository.findByPanAndSecurityCodeAndCardholderNameAndValidTo(pan, securityCode,cardHolderName,validTo);
    }
}
