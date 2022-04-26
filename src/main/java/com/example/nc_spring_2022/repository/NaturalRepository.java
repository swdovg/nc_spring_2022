package com.example.nc_spring_2022.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

@NoRepositoryBean
public interface NaturalRepository<T, Id extends Serializable> extends JpaRepository<T, Id> {
    Optional<T> findByNaturalId(Id naturalId);

    Optional<T> findByNaturalId(Map<String, Object> naturalIds);
}
