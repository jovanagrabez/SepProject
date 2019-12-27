package com.sep.kp.controller;

import com.sep.kp.model.DTO.AvailablePaymentMethodsHtmlModel;
import com.sep.kp.model.DTO.CreateTransactionDto;
import com.sep.kp.model.DTO.TransactionResultCustomerDTO;
import com.sep.kp.model.DTO.TransactionResultDTO;
import com.sep.kp.model.PaymentMethod;
import com.sep.kp.model.Transaction;
import com.sep.kp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/generate_url")
    public String createTransaction(@RequestBody CreateTransactionDto createTransactionDto) {
        return "https://localhost:8762/koncentrator_placanja/api/transaction/pay_method/" +this.transactionService.createTransaction(createTransactionDto).getIdHashValue();
    }

    @GetMapping(value = "pay_method/{hashed_id}")
    public ModelAndView selectionOfPayMethod(@PathVariable String hashedId) {
        Map<String, String> model = this.transactionService.generateHtmlForAvailablePayments(hashedId);

        return new ModelAndView("availablePaymentMethods", model);
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
