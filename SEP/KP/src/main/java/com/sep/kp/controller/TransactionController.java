package com.sep.kp.controller;

import com.sep.kp.model.Currency;
import com.sep.kp.model.DTO.*;
import com.sep.kp.model.PaymentRequest;
import com.sep.kp.model.Seller;
import com.sep.kp.model.Transaction;
import com.sep.kp.repository.SellerRepository;
import com.sep.kp.repository.TransactionRepository;
import com.sep.kp.service.PaymentRequestService;
import com.sep.kp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/transaction")
@CrossOrigin("https://localhost:5000")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PaymentRequestService paymentRequestService;

    private static final String Bitcoin_SERVICE_URI= "https://localhost:8762/bitcoin_service/api/order";


    @PostMapping("/generate_url")
    public String createTransaction(@RequestBody CreateTransactionDto createTransactionDto) {
        return "https://localhost:8762/koncentrator_placanja/api/transaction/pay_method/" +this.transactionService.createTransaction(createTransactionDto).getIdHashValue();
    }

    @GetMapping(value = "/pay_method/{hashedId}")
    public ModelAndView selectionOfPayMethod(@PathVariable String hashedId) {
        Map<String, String> model = this.transactionService.generateHtmlForAvailablePayments(hashedId);

        return new ModelAndView("availablePaymentMethods", model);
    }

    @GetMapping(value = "/bitcoin/{hashedId}")
    public RedirectView sendRedirectToBitcoin(@PathVariable String hashedId) {
        Transaction transaction = this.transactionRepository.findTransactionByIdHashValue(hashedId);
        Seller seller = this.sellerRepository.findSellerById(transaction.getSellerId());

        CreateBitcoinOrderDTO bitcoinOrderDTO = new CreateBitcoinOrderDTO(transaction.getId(), transaction.getAmount(), Currency.EUR,
                Currency.BTC, "Bitcoin transaction", "Bitcoin transaction id:" + transaction.getId(),
                null, null, null, null, seller.getBitcoinToken());


        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(bitcoinOrderDTO, requestHeaders);

        ResponseEntity<String> resp = restTemplate.postForEntity(Bitcoin_SERVICE_URI, requestEntity, String.class);

        return new RedirectView(resp.getBody());
    }


    @GetMapping(value = "/bank/{hashedId}")
    public RedirectView sendRedirectToBank(@PathVariable String hashedId) {
        Transaction transaction = this.transactionRepository.findTransactionByIdHashValue(hashedId);
        Seller seller = this.sellerRepository.findSellerById(transaction.getSellerId());

        PaymentRequest paymentRequest = paymentRequestService.createPaymentRequest(transaction.getProductId().toString(), transaction.getAmount());


        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(paymentRequest, requestHeaders);

        ResponseEntity<PaymentDTO> resp = restTemplate.postForEntity("https://localhost:8762/bank_service/api/get-payment-url", requestEntity, PaymentDTO.class);
        transaction.setTimestamp(new Date());
        transaction.setMerchantOrderId((long)resp.getBody().getMerchantOrderId());
        transaction.setPaymentId(Long.parseLong(resp.getBody().getPaymentId()));

        this.transactionRepository.save(transaction);
        return new RedirectView(resp.getBody().getPaymentUrl().concat('/' + hashedId));
    }

    @GetMapping(value = "/getTransaction/{hashedId}")
    public Transaction getTransaction (@PathVariable String hashedId){
        return this.transactionRepository.findTransactionByIdHashValue(hashedId);
    }

    @PostMapping(value = "/finish-transaction")
    public ResponseEntity<TransactionResultCustomerDTO> finishTransaction(@RequestBody TransactionResultDTO transactionDTO) {


        Transaction transaction = this.transactionRepository.findByMerchantOrderId( transactionDTO.getMerchantOrderId());
      /*  transaction.setAcquirerOrderId(transactionDTO.getAcquirerOrderId());
        transaction.setAmount(transactionDTO.getAmount());

        transaction.setMerchantOrderId(transactionDTO.getMerchantOrderId());
        transaction.setPaymentId(transactionDTO.getPaymentId());*/
        transaction.setStatus(transactionDTO.getStatus());
        transaction.setTimestamp(transactionDTO.getAcquirerTimestamp());
        this.transactionRepository.save(transaction);
        String resultUrl = transactionService.endTransaction(transaction);

        TransactionResultCustomerDTO transactionCustomer = new TransactionResultCustomerDTO(transaction.getMerchantOrderId(),
                transaction.getAcquirerOrderId(), transaction.getTimestamp(),
                transaction.getPaymentId(), resultUrl, transaction.getAmount(), transaction.getStatus());

        return ResponseEntity.ok().body(transactionCustomer);
    }

}
