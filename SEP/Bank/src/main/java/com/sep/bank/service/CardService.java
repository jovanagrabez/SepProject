package com.sep.bank.service;


import com.sep.bank.model.Card;
import org.springframework.stereotype.Service;

@Service
public interface CardService {

    Card find(String pan);
}
