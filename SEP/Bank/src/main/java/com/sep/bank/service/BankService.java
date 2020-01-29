package com.sep.bank.service;

import com.sep.bank.model.DTO.AcquirerDTO;
import com.sep.bank.model.DTO.CardAmountDTO;
import com.sep.bank.model.DTO.PaymentDTO;
import com.sep.bank.model.DTO.RequestDTO;
import com.sep.bank.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public interface BankService {


    PaymentDTO getPaymentUrl(RequestDTO requestDTO);
    Transaction checkBankForCard(CardAmountDTO card);
    String checkCard(AcquirerDTO acquirerDataDTO);

}
