package com.example.nc_spring_2022.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@IdClass(FeedbackId.class)
public class Feedback {
    @Id
    private Long consumerId;
    @Id
    private Long subscriptionId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private Integer rating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Feedback feedback = (Feedback) o;
        return consumerId != null && Objects.equals(consumerId, feedback.consumerId)
                && subscriptionId != null && Objects.equals(subscriptionId, feedback.subscriptionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consumerId, subscriptionId);
    }
}
