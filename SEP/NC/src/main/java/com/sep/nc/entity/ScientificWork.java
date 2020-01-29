package com.sep.nc.entity;

import com.sep.nc.entity.enumeration.ScientificArea;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
// TODO Fali pdf

@Table
@Entity
@Data
public class ScientificWork {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private ScientificArea scientificArea;

    @Column
    private String apstract;

    @ElementCollection
    private List<String> keyTerms;

    @Column
    private boolean accepted;




}
