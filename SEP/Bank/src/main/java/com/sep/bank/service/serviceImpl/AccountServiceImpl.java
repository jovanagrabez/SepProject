package com.sep.bank.service.serviceImpl;


import com.sep.bank.model.Account;
import com.sep.bank.repository.AccountRepository;
import com.sep.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);

    }

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account checkMerchantData(String merchantId, String merchantPassword) {
        return accountRepository.findByMerchantPasswordAndMerchantId(merchantPassword,merchantId);
    }





}
