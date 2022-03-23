package com.example.nc_spring_2022.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class FeedbackId implements Serializable {
    private Long consumerId;
    private Long subscriptionId;
}
