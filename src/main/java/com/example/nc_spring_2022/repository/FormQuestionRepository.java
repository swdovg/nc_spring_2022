package com.example.nc_spring_2022.repository;

import com.example.nc_spring_2022.model.FormQuestion;
import org.springframework.stereotype.Repository;

@Repository
public interface FormQuestionRepository extends NaturalRepository<FormQuestion, Long> {
}
