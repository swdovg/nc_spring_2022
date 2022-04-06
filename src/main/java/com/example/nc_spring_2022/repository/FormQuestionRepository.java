package com.example.nc_spring_2022.repository;

import com.example.nc_spring_2022.model.FormQuestion;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormQuestionRepository extends NaturalRepository<FormQuestion, Long> {
    List<FormQuestion> findAllBySubscriptionId(Long subscriptionId);
}
