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
    private String client;    // seller's token(id) from scientific centre

    @Column
    private String magazine;

    @Column
    private Long magazineId;

    @Column
    private String clientId;

    @Column
    private String clientPassword;

    @ManyToMany
    private List<PaymentMethod> paymentMethods;

    @Column
    private String bitcoinToken;

}
