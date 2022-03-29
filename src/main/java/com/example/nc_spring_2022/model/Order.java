package com.example.nc_spring_2022.model;

import com.example.nc_spring_2022.util.IdGenerator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NaturalId
    @Column(nullable = false, unique = true)
    private UUID businessKey = IdGenerator.createId();
    @OneToOne(targetEntity = Consumer.class)
    private Consumer consumer;
    @OneToOne(targetEntity = Subscription.class)
    private Subscription subscription;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;

        return getBusinessKey().equals(order.getBusinessKey());
    }

    @Override
    public int hashCode() {
        return getBusinessKey().hashCode();
    }
}
