package com.sep.pcc.service.serviceImpl;

import com.sep.pcc.AES;
import com.sep.pcc.model.Bank;
import com.sep.pcc.repository.BankRepository;
import com.sep.pcc.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankServiceImpl implements BankService {

    @Autowired
    BankRepository bankRepository;

    private AES aes;
    @Override
    public Bank findBank(String pan) {
        System.out.println("DOBIJENI PAN" +pan );

        String novipan = aes.decrypt(pan);
       System.out.println("LALALAL" +novipan.substring(0,6) );
        int bankNumber=Integer.parseInt(novipan.substring(0,6));
        return bankRepository.findByBankNumber(bankNumber);
    }
}
