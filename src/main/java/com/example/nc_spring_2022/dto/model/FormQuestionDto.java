package com.example.nc_spring_2022.dto.model;

import lombok.Data;

@Data
public class FormQuestionDto {
    private Long id;
    private Long subscriptionId;
    private String question;
}
