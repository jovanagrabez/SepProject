package com.sep.pcc.service;

import com.sep.pcc.model.Bank;
import org.springframework.stereotype.Service;

@Service
public interface BankService {
    Bank findBank(String accountNumber);

}
