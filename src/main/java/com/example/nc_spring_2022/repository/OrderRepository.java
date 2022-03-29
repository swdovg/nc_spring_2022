package com.example.nc_spring_2022.repository;

import com.example.nc_spring_2022.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends NaturalRepository<Order, Long> {
}
