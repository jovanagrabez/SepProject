package com.sep.bank.service;


import com.sep.bank.model.Card;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface CardService {

    Card find(String pan, int securityCode, String cardHolderName, Date validTo);
}
