package com.sep.bank.service;

import com.sep.bank.model.Account;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    void saveAccount(Account account);
    Account createAccount(Account account);
    Account checkMerchantData(String merchantId, String merchantPassword);

}
