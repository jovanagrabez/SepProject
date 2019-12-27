package com.sep.bank.service.serviceImpl;


import com.sep.bank.model.Account;
import com.sep.bank.model.Card;
import com.sep.bank.model.DTO.*;
import com.sep.bank.model.Transaction;
import com.sep.bank.repository.BankRepository;
import com.sep.bank.repository.TransactionRepository;
import com.sep.bank.service.AccountService;
import com.sep.bank.service.BankService;
import com.sep.bank.service.CardService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;


    @Autowired
    private CardService cardService;


    @Override
    public PaymentDTO getPaymentUrl(RequestDTO requestDTO) {
        Account account = accountService.checkMerchantData(requestDTO.getMerchantId(), requestDTO.getMerchantPassword());
        PaymentDTO paymentDTO = new PaymentDTO();
        if (account != null) {
            paymentDTO = new PaymentDTO(RandomStringUtils.randomAlphabetic(16), requestDTO.getAmount(),
                    RandomStringUtils.randomAlphabetic(16),  requestDTO.getMerchantOrderId());
        }

        return paymentDTO;

    }

    @Override
    public Transaction checkBankForCard(CardAmountDTO card) {
        Card foundCard = cardService.find(card.getPan());

        Transaction transaction = new Transaction();
        transaction.setMerchantOrderId(card.getMerchantOrderId());
        transaction.setPaymentId(card.getPaymentId());
        transaction.setAmount(card.getAmount());

        if (foundCard != null) {
            if (checkAmountOnAccount(foundCard, card.getAmount())) {

                transaction.setStatus("SUCCESS");
                System.out.println("LALALALALAL" + transaction.getStatus());
            } else
                transaction.setStatus("FAILED");
        } else {
            // Kada se implementira pcc ako su razliciti treba dopuniti metodu

        }
        transactionRepository.save(transaction);
        return transaction;
    }

    private boolean checkAmountOnAccount(Card foundCard, double amount) {

        System.out.println("BBBBBBBBBBBBBBBBBB"+foundCard.getAccount().getAmount());
        if (amount <= foundCard.getAccount().getAmount()) {
            foundCard.getAccount().setAmount(foundCard.getAccount().getAmount() - amount);
            accountService.saveAccount(foundCard.getAccount());
            return true;
        }
        return false;
    }

    @Override
    public String checkCard(AcquirerDTO acquirerDataDTO) {
        CardDTO card = acquirerDataDTO.getCard();
        Card foundCard = cardService.find(card.getPan());

        if (foundCard != null) {
            if (checkAmountOnAccount(foundCard, acquirerDataDTO.getAmount())) {
                return "SUCCESS";
            } else
                return "FAILED";
        }
        return null;
    }
}
