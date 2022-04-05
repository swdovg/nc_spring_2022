package com.example.nc_spring_2022.repository;

import com.example.nc_spring_2022.model.Order;
import com.example.nc_spring_2022.model.Subscription;
import com.example.nc_spring_2022.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends NaturalRepository<Order, Long> {
    List<Order> findAllByUserId(Long userId, Pageable pageable);

    Optional<Order> findByUserAndSubscription(User user, Subscription subscription);

    Optional<Order> findByUserIdAndSubscriptionId(Long userId, Long subscriptionId);
}
