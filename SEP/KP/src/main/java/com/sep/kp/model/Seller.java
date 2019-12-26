package com.sep.kp.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Seller implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String client;    // seller's token(id) from scientific centre

    @Column
    private String magazine;

    @Column
    private String clientId;

    @Column
    private String clientPassword;

    @ManyToOne
    private Method paymentMethod;


    public Seller(String magazine,String client, String clientId, String clientPassword, Method paymentMethod) {
        this.magazine = magazine;
        this.client=client;
        this.clientId = clientId;
        this.clientPassword = clientPassword;
        this.paymentMethod = paymentMethod;
    }

    public Seller() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMagazine() {
        return magazine;
    }

    public void setMagazine(String magazine) {
        this.magazine = magazine;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientPassword() {
        return clientPassword;
    }

    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }

    public Method getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Method paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
