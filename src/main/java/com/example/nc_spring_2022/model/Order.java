package com.example.nc_spring_2022.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

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
    @OneToOne(targetEntity = Consumer.class)
    private Consumer consumer;
    @OneToOne(targetEntity = Subscription.class)
    private Subscription subscription;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;

        if (getId() != null ? !getId().equals(order.getId()) : order.getId() != null) return false;
        if (getConsumer() != null ? !getConsumer().equals(order.getConsumer()) : order.getConsumer() != null)
            return false;
        return getSubscription() != null ? getSubscription().equals(order.getSubscription()) : order.getSubscription() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getConsumer() != null ? getConsumer().hashCode() : 0);
        result = 31 * result + (getSubscription() != null ? getSubscription().hashCode() : 0);
        return result;
    }
}
