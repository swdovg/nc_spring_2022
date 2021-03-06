package com.example.nc_spring_2022.model;

import com.example.nc_spring_2022.util.IdGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;
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
    @Column(nullable = false, columnDefinition = "text")
    private String description;
    @Column(nullable = false)
    private Long price;
    @Column
    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.RUB;
    @Column
    private Long ratings = 0L;
    @Column
    private Long quantityOfFeedbacks = 0L;
    @OneToOne(targetEntity = User.class)
    private User supplier;
    @OneToOne(targetEntity = Category.class)
    private Category category;
    @OneToMany(targetEntity = FormQuestion.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormQuestion> questions;
    @OneToMany(targetEntity = Order.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
    private Long imageId;
    private String imageUrl;

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
