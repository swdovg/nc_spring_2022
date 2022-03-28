package com.example.nc_spring_2022.repository;

import com.example.nc_spring_2022.model.FormQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormQuestionRepository extends JpaRepository<FormQuestion, Long> {
}
