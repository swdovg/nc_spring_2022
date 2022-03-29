package com.example.nc_spring_2022.repository;

import com.example.nc_spring_2022.model.Consumer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsumerRepository extends NaturalRepository<Consumer, Long> {
    Optional<Consumer> findByEmail(String email);
}
