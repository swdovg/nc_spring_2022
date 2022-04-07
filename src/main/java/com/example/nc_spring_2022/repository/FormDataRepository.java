package com.example.nc_spring_2022.repository;

import com.example.nc_spring_2022.model.FormData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormDataRepository extends NaturalRepository<FormData, Long> {
    List<FormData> findAllByOrderId(Long orderId);
}
