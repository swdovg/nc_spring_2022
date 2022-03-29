package com.example.nc_spring_2022.repository;

import com.example.nc_spring_2022.model.Feedback;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends NaturalRepository<Feedback, Long> {
}
