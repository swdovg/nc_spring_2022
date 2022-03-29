package com.example.nc_spring_2022.repository;

import com.example.nc_spring_2022.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends NaturalRepository<Category, Long> {
}
