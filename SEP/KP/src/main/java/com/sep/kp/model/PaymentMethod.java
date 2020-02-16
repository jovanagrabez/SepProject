package com.sep.kp.model;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class PaymentMethod implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String createTransactionURI;
    @Column
    private String checkStatusURI;
    @Column
    private boolean isBank;

}
