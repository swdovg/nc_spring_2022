package com.example.nc_spring_2022.repository;

import com.example.nc_spring_2022.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackRepository extends NaturalRepository<Feedback, Long> {
    Page<Feedback> findAllBySubscriptionId(Long subscriptionId, Pageable pageable);

    Optional<Feedback> findByConsumerIdAndSubscriptionId(Long userId, Long subscriptionId);
}
