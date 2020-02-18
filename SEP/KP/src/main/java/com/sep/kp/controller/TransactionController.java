package com.sep.kp.controller;

import com.sep.kp.model.*;
import com.sep.kp.model.DTO.*;
import com.sep.kp.repository.PaymentMethodRepository;
import com.sep.kp.repository.SellerRepository;
import com.sep.kp.repository.TransactionRepository;
import com.sep.kp.service.PaymentRequestService;
import com.sep.kp.service.SellerService;
import com.sep.kp.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/transaction")
@CrossOrigin({"https://localhost:5000", "https://localhost:4201"})
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
    private SellerService sellerService;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    private static final String NC_SERVICE_URI = "https://localhost:8762/naucna_centrala/api/magazine/finish";
    private static final String NC_FRONTEND = "https://localhost:4200";
    @Autowired
    private PaymentRequestService paymentRequestService;



    @PostMapping("/generate_url")
    public String createTransaction(@RequestBody CreateTransactionDto createTransactionDto) {
        log.info("Created url for redirect to payment concentrator, for product id: "+createTransactionDto.getProductId() + "name= "+createTransactionDto.getProductName());
        return "https://localhost:4201/payment/" +this.transactionService.createTransaction(createTransactionDto).getIdHashValue();
    }

    @GetMapping(value = "/pay_method/{hashedId}")
    public List<AvailablePaymentMethodsDto> selectionOfPayMethod(@PathVariable String hashedId) {
        List<AvailablePaymentMethodsDto> model = this.transactionService.getAvailablePayments(hashedId);

        log.info("Generated payment concentrator page");
        return model;
    }

    @GetMapping(value = "/{paymentMethodId}/{hashedId}")
    public RedirectView sendRedirectToPayService(@PathVariable Long paymentMethodId, @PathVariable String hashedId) {
        PaymentMethod paymentMethod = this.paymentMethodRepository.getOne(paymentMethodId);
        Transaction transaction = this.transactionRepository.findTransactionByIdHashValue(hashedId);
        Seller seller = this.sellerRepository.findSellerById(transaction.getSellerId());

        String bankName = "";


        CreateOrderDto createOrderDto = new CreateOrderDto();
        createOrderDto.setPriceAmount(transaction.getAmount());
        createOrderDto.setPriceCurrency(Currency.EUR);
        createOrderDto.setReceiveCurrency(Currency.BTC);
        createOrderDto.setDescription("Transaction id: " + transaction.getId());
        createOrderDto.setHashedOrderId(transaction.getIdHashValue());
        createOrderDto.setOrderId(transaction.getId());
        createOrderDto.setTitle("New transaction");

        createOrderDto.setParams(new HashMap<>());
        for (PaymentData paymentData : seller.getPaymentsData()) {
            createOrderDto.getParams().put(paymentData.getName(), paymentData.getValue());
            if (paymentData.getName().equals("bankName")) {
                bankName = paymentData.getValue();
            }
        }
        if (paymentMethod.isBank()) {
            transaction.setSelectedPaymentMethodURI("https://localhost:8762/".concat(bankName)+"/api/status");
        } else {
            transaction.setSelectedPaymentMethodURI(paymentMethod.getCheckStatusURI());
        }
        transaction = transactionRepository.save(transaction);
        log.info("Selected "+paymentMethod.getName()+" payment service for transaction id: "+ transaction.getId());

//        createOrderDto.setParams(
//        createOrderDto.setBitcoinToken(seller.getBitcoinToken());
//        createOrderDto.setPaymentIntent(PayPalPaymentIntent.ORDER);
//        createOrderDto.setPaymentMethod(PayPalPaymentMethod.PAYPAL);
//        createOrderDto.setMerchantId(seller.getId());
//        createOrderDto.setMerchantPassword("123");
//        Random random = new Random();
//        createOrderDto.setMerchantOrderId((long)random.nextInt());
//        createOrderDto.setMerchantTimestamp(new Date());
        // TODO prebaciti success error i failed url da se setuje u banci

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(createOrderDto, requestHeaders);

        if (paymentMethod.isBank()) {
            paymentMethod.setCreateTransactionURI("https://localhost:8762/".concat(bankName)+"/api/get-payment-url");
        }
        ResponseEntity<String> resp = restTemplate.postForEntity(paymentMethod.getCreateTransactionURI(), requestEntity, String.class);

        log.info("Redirect to "+ paymentMethod.getName() +" api page");
        return new RedirectView(resp.getBody());
    }

    @GetMapping(value = "/success/{hashedOrderId}")
    public RedirectView successOrder(@PathVariable String hashedOrderId) {
        Transaction transaction = this.transactionRepository.findTransactionByIdHashValue(hashedOrderId);
        log.info("Transaction id: "+transaction.getId()+" successfully finished.");

        transaction.setStatus(TransactionStatus.Paid);
        transactionRepository.save(transaction);

        FinishedMagazineOrderDto finishedMagazineOrderDto = new FinishedMagazineOrderDto();
        finishedMagazineOrderDto.setEmail(transaction.getBuyerEmail());
        finishedMagazineOrderDto.setPurchaseStatus(PurchaseStatus.Paid);
        finishedMagazineOrderDto.setPurchaseId(transaction.getScientificCenterPurchaseId());

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
        transaction.setStatus(TransactionStatus.Cancelled);
        log.warn("Transaction id: "+transaction.getId()+" cancelled or has an error.");

        this.transactionRepository.save(transaction);

        FinishedMagazineOrderDto finishedMagazineOrderDto = new FinishedMagazineOrderDto();
        finishedMagazineOrderDto.setEmail(transaction.getBuyerEmail());
        finishedMagazineOrderDto.setPurchaseStatus(PurchaseStatus.Cancelled);
        finishedMagazineOrderDto.setPurchaseId(transaction.getScientificCenterPurchaseId());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(finishedMagazineOrderDto, requestHeaders);

        ResponseEntity<String> resp = restTemplate.postForEntity(NC_SERVICE_URI, requestEntity, String.class);
        return new RedirectView(NC_FRONTEND);

    }

    @GetMapping(value = "/getTransaction/{hashedId}")
    public Transaction getTransaction (@PathVariable String hashedId){
        return this.transactionRepository.findTransactionByIdHashValue(hashedId);
    }

    @PostMapping(value = "/finish-transaction")
    public String finishTransaction(@RequestBody TransactionResultDTO transactionDTO) {


        Transaction transaction = this.transactionRepository.findTransactionByIdHashValue( transactionDTO.getHashedOrderId());

        transaction.setTimestamp(transactionDTO.getAcquirerTimestamp());
        this.transactionRepository.save(transaction);
        String resultUrl = transactionService.endTransaction(transaction);

        return "https://localhost:4200";
    }

    @GetMapping(value = "purchase-status/{purchaseId}")
    public ResponseEntity<String> checkPurchaseStatus(@PathVariable Long purchaseId) {
        Transaction transaction = this.transactionService.getTransactionByMagazinePurchaseId(purchaseId);

//        if(transaction != null) {
//            if (transaction.getStatus().equals(TransactionStatus.Paid) || transaction.getStatus().equals(TransactionStatus.Cancelled)) {
//                return ResponseEntity.ok(transaction.getStatus());
//            }
//        } else {
//            return ResponseEntity.ok(TransactionStatus.New);
//        }

        if (transaction == null || transaction.getResultUrl() == null) {
            return ResponseEntity.ok(TransactionStatus.Cancelled.toString());
        }

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(requestHeaders);

        ResponseEntity<String> resp = restTemplate.getForEntity(transaction.getSelectedPaymentMethodURI()+"/"+transaction.getIdHashValue(), String.class, requestEntity);

        switch (resp.getBody()){
            case "Paid": return ResponseEntity.ok(TransactionStatus.Paid.toString());
            case "Cancelled": return ResponseEntity.ok(TransactionStatus.Cancelled.toString());
            case "New": return ResponseEntity.ok(TransactionStatus.New.toString());
        }

        return ResponseEntity.ok(transaction.getStatus().toString());
    }


    @GetMapping(value = "/getSeller/{hashedId}")
    public Seller getSeller (@PathVariable String hashedId){

    return this.sellerService.getSellerByHashedTransactionId(hashedId);

    }

    @GetMapping(value = "/getMerchantId/{sellerId}")
    public PaymentData getMerchantId (@PathVariable String sellerId){
        PaymentData paymentData = new PaymentData();
        Seller s = this.sellerRepository.getOne(Long.parseLong(sellerId));
        for(PaymentData data :s.getPaymentsData()){
            if (data.getName().equals("merchantId")){
              paymentData = data;
            }
        }
        return paymentData;
    }

}
