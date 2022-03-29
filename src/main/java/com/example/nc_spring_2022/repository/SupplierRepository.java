package com.example.nc_spring_2022.repository;

import com.example.nc_spring_2022.model.Supplier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends NaturalRepository<Supplier, Long> {
    Optional<Supplier> findByEmail(String email);
}
