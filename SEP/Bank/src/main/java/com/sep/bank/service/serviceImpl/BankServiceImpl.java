package com.sep.bank.service.serviceImpl;


import com.sep.bank.AES;
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
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Random;

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

    @Autowired
    RestTemplate restTemplate;

    private AES aes;



    @Override
    public PaymentDTO getPaymentUrl(RequestDTO requestDTO) {
        LOGGER.info("Processing KP request: " + requestDTO);

        Account account = accountService.checkMerchantData(requestDTO.getParams().get("merchantId"), requestDTO.getParams().get("merchantPassword"));
        LOGGER.info("Returning account: " + account);


        PaymentDTO paymentDTO = new PaymentDTO();
        if (account != null) {
            LOGGER.info("Account exists " );

            paymentDTO = new PaymentDTO(RandomStringUtils.randomNumeric(16), requestDTO.getPriceAmount(),
                    "https://localhost:5000/home".concat("/"+requestDTO.getParams().get("bankName")+"/" + requestDTO.getHashedOrderId()),  requestDTO.getMerchantOrderId());

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
        LOGGER.info("Merchant id: " + card.getMerchantId());

        String pan = aes.encrypt(card.getPan());
        card.setPan(pan);
        System.out.println("USAO I NASAO" + pan);
        Card foundCard = cardService.find(card.getPan());

        Transaction transaction = new Transaction();
        transaction.setSellerId(card.getSellerId());
        transaction.setHashedOrderId(card.getHashedId());
        transaction.setMerchantOrderId(card.getMerchantOrderId());
        transaction.setPaymentId(card.getPaymentId());
        transaction.setAmount(card.getAmount());

        if (foundCard != null) {
            if (checkAmountOnAccount(foundCard, card.getAmount())) {
                Account a = this.accountRepository.findByMerchantId(card.getMerchantId());
                a.setAmount(a.getAmount()+card.getAmount());
                this.accountRepository.save(a);
                transaction.setStatus("SUCCESS");
                LOGGER.info("Transaction status: " + transaction.getStatus());

                System.out.println("LALALALALAL" + transaction.getStatus());
            } else

                transaction.setStatus("FAILED");
            LOGGER.info("Transaction status: " + transaction.getStatus());

        } else {
            LOGGER.error("Card not found , card with pan: " + card.getPan() );

            transaction = forwardToPcc(card, transaction);
                Account a = this.accountRepository.findByMerchantId(card.getMerchantId());
                a.setAmount(a.getAmount()+card.getAmount());
                this.accountRepository.save(a);




        }

        LOGGER.info("Saving this transaction: " + transaction);

        transactionRepository.save(transaction);
        return transaction;
    }

    private boolean checkAmountOnAccount(Card foundCard, double amount) {

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
        System.out.println("forwarded lala" + card.getPan());
        Card foundCard = cardService.find(card.getPan());

        if (foundCard != null) {
            if (checkAmountOnAccount(foundCard, acquirerDataDTO.getAmount())) {
                return "SUCCESS";
            } else
                return "FAILED";
        }
        return null;
    }

    private Transaction forwardToPcc(CardAmountDTO cardAmountDTO, Transaction transaction) {
        Random random = new Random();
        CardDTO cardDTO = new CardDTO(cardAmountDTO.getPan(), cardAmountDTO.getSecurityCode(),
                cardAmountDTO.getCardHolderName(), cardAmountDTO.getValidTo());

        AcquirerDTO acquirerDataDTO = new AcquirerDTO(random.nextLong(), new Date(), transaction.getAmount(), cardDTO);
        transaction.setTimestamp(acquirerDataDTO.getAcquirerTimestamp());
        transaction.setAcquirerOrderId(acquirerDataDTO.getAcquirerOrderId());

        // poziva PCC koji prosledjuje podatke banci kupca i vraca status
        PaymentResultDTO paymentResultDTO = restTemplate.postForObject("https://localhost:8762/pcc/api/forward-to-bank",
                acquirerDataDTO, PaymentResultDTO.class);

        if (paymentResultDTO != null) {
            transaction.setStatus(paymentResultDTO.getStatus());
        }
        return transaction;
    }



}
