package com.example.nc_spring_2022.repository;

import com.example.nc_spring_2022.model.Subscription;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends NaturalRepository<Subscription, Long> {
}
