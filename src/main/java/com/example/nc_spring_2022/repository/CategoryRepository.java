package com.example.nc_spring_2022.repository;

import com.example.nc_spring_2022.model.Category;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends NaturalRepository<Category, Long> {
    List<Category> findAllByParentId(Long parentId);

    Optional<Category> findByName(String name);
}
