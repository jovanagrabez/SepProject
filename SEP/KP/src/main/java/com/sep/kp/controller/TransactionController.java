package com.sep.kp.controller;

import com.sep.kp.model.*;
import com.sep.kp.model.DTO.*;
import com.sep.kp.repository.SellerRepository;
import com.sep.kp.repository.TransactionRepository;
import com.sep.kp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private RestTemplate restTemplate;

    private static final String Bitcoin_SERVICE_URI = "https://localhost:8762/bitcoin_service/api/order";
    private static final String PayPal_SERVICE_URI = "https://localhost:8762/paypal_service/api/pay";
    private static final String NC_SERVICE_URI = "https://localhost:8762/naucna_centrala/api/magazine/finish";
    private static final String NC_FRONTEND = "https://localhost:4200";


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

        CreateBitcoinOrderDTO bitcoinOrderDTO = new CreateBitcoinOrderDTO(transaction.getId(), transaction.getIdHashValue(),
                transaction.getAmount(), Currency.EUR, Currency.BTC, "Bitcoin transaction",
                "Bitcoin transaction id:" + transaction.getId(),
                null, null, null, null, seller.getBitcoinToken());


        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(bitcoinOrderDTO, requestHeaders);

        ResponseEntity<String> resp = restTemplate.postForEntity(Bitcoin_SERVICE_URI, requestEntity, String.class);

        return new RedirectView(resp.getBody());
    }

    @GetMapping(value = "/paypal/{hashedId}")
    public RedirectView sendRedirectToPayPal(@PathVariable String hashedId) {
        Transaction transaction = this.transactionRepository.findTransactionByIdHashValue(hashedId);
        Seller seller = this.sellerRepository.findSellerById(transaction.getSellerId());

        CreatePayPalOrderDto createPayPalOrderDto = new CreatePayPalOrderDto();
        createPayPalOrderDto.setPrice(transaction.getAmount());
        createPayPalOrderDto.setCurrency("EUR");
        createPayPalOrderDto.setNameOfJournal(transaction.getProductId().toString());
        createPayPalOrderDto.setPaymentIntent(PayPalPaymentIntent.ORDER);
        createPayPalOrderDto.setPaymentMethod(PayPalPaymentMethod.PAYPAL);
        createPayPalOrderDto.setDescription("It`s OK");
        createPayPalOrderDto.setHashedMagazineId(transaction.getIdHashValue());


        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(createPayPalOrderDto, requestHeaders);

        ResponseEntity<String> resp = restTemplate.postForEntity(PayPal_SERVICE_URI, requestEntity, String.class);

        return new RedirectView(resp.getBody());
    }

    @GetMapping(value = "/success/{hashedOrderId}")
    public RedirectView successOrder(@PathVariable String hashedOrderId) {
        Transaction transaction = this.transactionRepository.findTransactionByIdHashValue(hashedOrderId);
        transaction.setStatus("SUCCESS");

        FinishedMagazineOrderDto finishedMagazineOrderDto = new FinishedMagazineOrderDto();
        finishedMagazineOrderDto.setEmail(transaction.getBuyerEmail());
        finishedMagazineOrderDto.setMagazineId(transaction.getProductId());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(finishedMagazineOrderDto, requestHeaders);

        ResponseEntity<String> resp = restTemplate.postForEntity(NC_SERVICE_URI, requestEntity, String.class);
        return new RedirectView(NC_FRONTEND);
    }

    @GetMapping(value = "/cancel/{hashedOrderId}")
    public RedirectView cancelOrder(@PathVariable String hashedOrderId) {
        Transaction transaction = this.transactionRepository.findTransactionByIdHashValue(hashedOrderId);
        transaction.setStatus("FAILED");

        this.transactionRepository.save(transaction);
        return new RedirectView(NC_FRONTEND);
    }


    @PostMapping(value = "/finish-transaction")
    public ResponseEntity<TransactionResultCustomerDTO> finishTransaction(@RequestBody TransactionResultDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setAcquirerOrderId(transactionDTO.getAcquirerOrderId());
        transaction.setAmount(transactionDTO.getAmount());

        transaction.setMerchantOrderId(transactionDTO.getMerchantOrderId());
        transaction.setPaymentId(transactionDTO.getPaymentId());
        transaction.setStatus(transactionDTO.getStatus());
        transaction.setTimestamp(transactionDTO.getAcquirerTimestamp());
        String resultUrl = transactionService.endTransaction(transaction);

        TransactionResultCustomerDTO transactionCustomer = new TransactionResultCustomerDTO(transaction.getMerchantOrderId(),
                transaction.getAcquirerOrderId(), transaction.getTimestamp(),
                transaction.getPaymentId(), resultUrl, transaction.getAmount(), transaction.getStatus());

        return ResponseEntity.ok().body(transactionCustomer);
    }

}
