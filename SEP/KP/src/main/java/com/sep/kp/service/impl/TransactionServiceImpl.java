package com.sep.kp.service.impl;


import com.sep.kp.model.DTO.AvailablePaymentMethodsDto;
import com.sep.kp.model.DTO.CreateTransactionDto;
import com.sep.kp.model.*;
import com.sep.kp.repository.SellerRepository;
import com.sep.kp.repository.TransactionRepository;
import com.sep.kp.service.PaymentRequestService;
import com.sep.kp.service.TransactionService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Argon2 ARGON2 = Argon2Factory.create();

    private static final int ITERATIONS = 2;
    private static final int MEMORY= 65536;
    private static final int PARALLELISM = 1;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private PaymentRequestService paymentRequestService;

    @Override
    public String endTransaction(Transaction transaction) {

     //   transaction.setStatus("SUCCESS");
        transactionRepository.save(transaction);
        PaymentRequest paymentRequest = paymentRequestService.getPaymentRequest(transaction.getMerchantOrderId());
        TransactionStatus status = transaction.getStatus();
        String url;
        if (status == null){
            url = paymentRequest.getErrorUrl();
        } else if (status.equals(TransactionStatus.Paid)) {
            url = paymentRequest.getSuccessUrl();
        } else if (status.equals(TransactionStatus.Cancelled)) {
            url = paymentRequest.getFailedUrl();
        } else {
            url = "error"; // paymentRequest.getErrorUrl();
        }
//        switch (status) {
//            case "SUCCESS":
//                url = paymentRequest.getSuccessUrl();
//                break;
//            case "FAILED":
//                url = paymentRequest.getFailedUrl();
//                break;
//            default:
//                url = paymentRequest.getErrorUrl();
//                break;
//        }
        return url;
    }

    @Override
    public Transaction createTransaction(CreateTransactionDto createTransactionDto) {
        Transaction transaction = new Transaction();
        transaction.setProductId(createTransactionDto.getProductId());
        transaction.setBuyerEmail(createTransactionDto.getUserEmail());
        transaction.setAmount(createTransactionDto.getPrice());
        transaction.setTypeOfProduct(createTransactionDto.getTypeOfProduct());
        transaction.setScientificCenterPurchaseId(createTransactionDto.getPurchaseId());
        transaction.setStatus(TransactionStatus.New);
        transaction.setScientificCenterPurchaseId(createTransactionDto.getPurchaseId());

        Seller seller = this.sellerRepository.findSellerByMagazineId(createTransactionDto.getProductId());  // TODO ako je naucni rad nece raditi jer poredi po id-u samo magazina, treba mozda uvek slati od magazina
        transaction.setSellerId(seller.getId());

        // generating hash value of id for url access to transaction
        Transaction savedTransaction = this.transactionRepository.save(transaction);
        final String hash = ARGON2.hash(ITERATIONS, MEMORY, PARALLELISM, savedTransaction.getId().toString());

        // posto url ne funkcionise sa tim parametrima
        String finalHash = hash.replace("$", "");
        finalHash = finalHash.replace("/", "");
        finalHash = finalHash.replace(":", "");
        finalHash = finalHash.replace("=", "");
        finalHash = finalHash.replace(",", "");

        savedTransaction.setIdHashValue(finalHash);

        return this.transactionRepository.save(savedTransaction);
    }

    @Override
    public List<AvailablePaymentMethodsDto> getAvailablePayments(String hashedId) {

        Transaction transaction = this.transactionRepository.findTransactionByIdHashValue(hashedId);
        Seller seller = this.sellerRepository.findSellerById(transaction.getSellerId());

        List<AvailablePaymentMethodsDto> availablePaymentMethods = new ArrayList<>();
        for (PaymentMethod method : seller.getPaymentMethods()) {
            availablePaymentMethods.add(new AvailablePaymentMethodsDto(method.getId(), method.getName(), method.getImageAssociated(),
                    "https://localhost:8762/koncentrator_placanja/api/transaction/".concat(method.getId().toString()).concat("/").concat(hashedId)));
        }
        return availablePaymentMethods;
    }

    @Override
    public Transaction getTransactionByMagazinePurchaseId(Long id) {

        return this.transactionRepository.findTransactionByScientificCenterPurchaseId(id);
    }
}
