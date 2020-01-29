package com.sep.kp.model.DTO;

import com.sep.kp.model.TypeOfProduct;
import lombok.Data;

@Data
public class CreateTransactionDto {

    private Long productId;
    private String productName;
    private String userEmail;
    private double price;
    private TypeOfProduct typeOfProduct;
    private Long purchaseId;

    public CreateTransactionDto(Long productId, String productName, String userEmail, double price, TypeOfProduct typeOfProduct, Long purchaseId) {
        this.productId = productId;
        this.productName = productName;
        this.userEmail = userEmail;
        this.price = price;
        this.typeOfProduct = typeOfProduct;
        this.purchaseId = purchaseId;
    }
}
