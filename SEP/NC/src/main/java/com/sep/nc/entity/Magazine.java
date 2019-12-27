package com.sep.nc.entity;

import com.sep.nc.entity.enumeration.PaymentType;
import com.sep.nc.entity.enumeration.ScientificArea;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
public class Magazine {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String ISSNNumber;

    @Column
    @Enumerated
    @ElementCollection(targetClass = ScientificArea.class)
    private List<ScientificArea> scientificAreas;

    @Column
    private PaymentType paymentType;

    @ManyToOne
    private User mainEditor;

    @OneToMany
    private List<User> scientificAreasEditors;

    @ManyToMany
    @JoinTable(name = "magazine_reviewer",
            joinColumns = @JoinColumn(name = "magazine_id"),
            inverseJoinColumns = @JoinColumn(name = "reviewer_id"))
    private List<User> reviewers;




}
