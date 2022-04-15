package com.example.nc_spring_2022.repository;

import com.example.nc_spring_2022.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends NaturalRepository<Subscription, Long> {
    Page<Subscription> findAllByCategoryId(Long categoryId, Pageable pageable);
}
