package com.example.nc_spring_2022.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Embeddable
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Long price;
    @Column
    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.RUB;
    @Column
    private Double averageRating = 0.0;
    @OneToOne(targetEntity = Supplier.class)
    private Supplier supplier;
    @OneToOne(targetEntity = Category.class)
    private Category category;
}
