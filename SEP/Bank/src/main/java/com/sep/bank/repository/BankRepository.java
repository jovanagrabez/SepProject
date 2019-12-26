package com.sep.bank.repository;

import com.sep.bank.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    Bank findByName(String name);
}
