package com.example.nc_spring_2022.repository;

import com.example.nc_spring_2022.model.Location;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends NaturalRepository<Location, Long> {
}
