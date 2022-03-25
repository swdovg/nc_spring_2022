package com.example.nc_spring_2022.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

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
        if (!(o instanceof Feedback feedback)) return false;

        if (getConsumerId() != null ? !getConsumerId().equals(feedback.getConsumerId()) : feedback.getConsumerId() != null)
            return false;
        if (getSubscriptionId() != null ? !getSubscriptionId().equals(feedback.getSubscriptionId()) : feedback.getSubscriptionId() != null)
            return false;
        if (getTitle() != null ? !getTitle().equals(feedback.getTitle()) : feedback.getTitle() != null) return false;
        if (getText() != null ? !getText().equals(feedback.getText()) : feedback.getText() != null) return false;
        return getRating() != null ? getRating().equals(feedback.getRating()) : feedback.getRating() == null;
    }

    @Override
    public int hashCode() {
        int result = getConsumerId() != null ? getConsumerId().hashCode() : 0;
        result = 31 * result + (getSubscriptionId() != null ? getSubscriptionId().hashCode() : 0);
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getText() != null ? getText().hashCode() : 0);
        result = 31 * result + (getRating() != null ? getRating().hashCode() : 0);
        return result;
    }
}
