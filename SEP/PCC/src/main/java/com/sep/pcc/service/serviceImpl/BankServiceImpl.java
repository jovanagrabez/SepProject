package com.sep.pcc.service.serviceImpl;

import com.sep.pcc.model.Bank;
import com.sep.pcc.repository.BankRepository;
import com.sep.pcc.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankServiceImpl implements BankService {

    @Autowired
    BankRepository bankRepository;
    @Override
    public Bank findBank(String pan) {
        int bankNumber=Integer.parseInt(pan.substring(0,6));
        return bankRepository.findByBankNumber(bankNumber);
    }
}
