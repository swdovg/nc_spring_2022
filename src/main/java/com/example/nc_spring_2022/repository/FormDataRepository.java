package com.example.nc_spring_2022.repository;

import com.example.nc_spring_2022.model.FormData;
import org.springframework.stereotype.Repository;

@Repository
public interface FormDataRepository extends NaturalRepository<FormData, Long> {
}
