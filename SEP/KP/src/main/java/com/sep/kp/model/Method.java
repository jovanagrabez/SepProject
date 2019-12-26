package com.sep.kp.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Method implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public Method(String name) {
        this.name = name;
    }

    public Method() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}