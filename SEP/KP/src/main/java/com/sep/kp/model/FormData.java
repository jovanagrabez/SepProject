package com.sep.kp.model;

import lombok.Data;

import javax.persistence.*;

@Table
@Entity
@Data
public class FormData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;


    @Column
    private String code;

    @Column
    private FormFieldType type;

    private String value;
}
