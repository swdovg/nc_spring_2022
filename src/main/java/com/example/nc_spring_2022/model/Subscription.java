package com.example.nc_spring_2022.model;

import com.example.nc_spring_2022.util.IdGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Embeddable
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NaturalId
    @Column(nullable = false, unique = true)
    private UUID businessKey = IdGenerator.createId();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscription that)) return false;

        return getBusinessKey().equals(that.getBusinessKey());
    }

    @Override
    public int hashCode() {
        return getBusinessKey().hashCode();
    }
}
