package com.example.nc_spring_2022.repository;

import com.example.nc_spring_2022.model.Category;
import com.example.nc_spring_2022.model.Subscription;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends NaturalRepository<Subscription, Long> {
    List<Subscription> findAllByCategory(Category category, Pageable pageable);
}
