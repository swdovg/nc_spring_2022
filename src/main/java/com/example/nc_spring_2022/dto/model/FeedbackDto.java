package com.example.nc_spring_2022.dto.model;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class FeedbackDto {
    private Long subscriptionId;
    private String userName;
    private String title;
    private String text;
    @Positive
    private Integer rating;
}
