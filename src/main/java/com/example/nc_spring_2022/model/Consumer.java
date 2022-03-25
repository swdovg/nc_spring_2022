package com.example.nc_spring_2022.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Embeddable
public class Consumer implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String phoneNumber;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.RUB;
    @OneToOne(targetEntity = Location.class)
    private Location defaultLocation;
}
