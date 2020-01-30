package com.sep.kp.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Seller implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long clientId;    // seller's token(id) from scientific centre

    @Column
    private String magazine;        // TODO promeniti u listu magazina

    @Column
    private Long magazineId;

    @Column
    private String clientPassword;

    @ManyToMany
    private List<PaymentMethod> paymentMethods;

    @Column
    private String bitcoinToken;

    @Column
    private String bankName;

    @Column
    private Long userId;

    @Column(nullable = true)
    private String merchantId;

    @Column
    private String paypalClientId;

    @Column
    private String paypalClientSecret;
}
