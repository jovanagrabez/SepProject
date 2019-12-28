package com.sep.bank.service.serviceImpl;


import com.sep.bank.model.Account;
import com.sep.bank.model.Card;
import com.sep.bank.model.DTO.*;
import com.sep.bank.model.Transaction;
import com.sep.bank.repository.AccountRepository;
import com.sep.bank.repository.BankRepository;
import com.sep.bank.repository.TransactionRepository;
import com.sep.bank.service.AccountService;
import com.sep.bank.service.BankService;
import com.sep.bank.service.CardService;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankServiceImpl implements BankService {

    private final Logger LOGGER = LoggerFactory.getLogger(BankServiceImpl.class);


    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardService cardService;


    @Override
    public PaymentDTO getPaymentUrl(RequestDTO requestDTO) {
        LOGGER.info("Processing KP request: " + requestDTO);

        Account account = accountService.checkMerchantData(requestDTO.getMerchantId(), requestDTO.getMerchantPassword());
        LOGGER.info("Returning account: " + account);


        PaymentDTO paymentDTO = new PaymentDTO();
        if (account != null) {
            LOGGER.info("Account exists " );

            paymentDTO = new PaymentDTO(RandomStringUtils.randomNumeric(16), requestDTO.getAmount(),
                    "https://localhost:5000/home",  requestDTO.getMerchantOrderId());

            LOGGER.info("Generating paymentId " + paymentDTO.getPaymentId());
            LOGGER.info("Generating paymentURL: " + paymentDTO.getPaymentUrl());
            LOGGER.info("Price of magazine: " + paymentDTO.getAmount());
            LOGGER.info("Order id: " + requestDTO.getMerchantOrderId());




        }

        return paymentDTO;

    }

    @Override
    public Transaction checkBankForCard(CardAmountDTO card) {
        LOGGER.info("Finding card: " + card);

        Card foundCard = cardService.find(card.getPan());

        Transaction transaction = new Transaction();
        transaction.setSellerId(card.getSellerId());
        transaction.setHashedOrderId(card.getHashedId());
        transaction.setMerchantOrderId(card.getMerchantOrderId());
        transaction.setPaymentId(card.getPaymentId());
        transaction.setAmount(card.getAmount());

        if (foundCard != null) {
            if (checkAmountOnAccount(foundCard, card.getAmount())) {
                Account a = this.accountRepository.findByMerchantId(card.getSellerId().toString());
                a.setAmount(a.getAmount()+card.getAmount());
                this.accountRepository.save(a);
                transaction.setStatus("SUCCESS");
                LOGGER.info("Transaction status: " + transaction.getStatus());

                System.out.println("LALALALALAL" + transaction.getStatus());
            } else

                transaction.setStatus("FAILED");
            LOGGER.info("Transaction status: " + transaction.getStatus());

        } else {
            LOGGER.error("Card not found , car with pan: " + card.getPan() );

            transaction.setStatus("FAILED");
            // TODO Kada se implementira pcc ako su razliciti treba dopuniti metodu

        }

        LOGGER.info("Saving this transaction: " + transaction);

        transactionRepository.save(transaction);
        return transaction;
    }

    private boolean checkAmountOnAccount(Card foundCard, double amount) {

        System.out.println("BBBBBBBBBBBBBBBBBB"+foundCard.getAccount().getAmount());
        if (amount <= foundCard.getAccount().getAmount()) {
            foundCard.getAccount().setAmount(foundCard.getAccount().getAmount() - amount);
            LOGGER.info("Amount after paying: " + foundCard.getAccount().getAmount());

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
