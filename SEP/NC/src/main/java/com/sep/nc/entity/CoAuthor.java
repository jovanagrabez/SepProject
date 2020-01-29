package com.sep.nc.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class CoAuthor {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String country;

    @Column
    private String city;

    @ManyToOne
    private ScientificWork scientificWork;
}
