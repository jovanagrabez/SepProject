package com.sep.bank.repository;


import com.sep.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Account findByMerchantPasswordAndMerchantId(String merchantPassword, String merchantId);
}
