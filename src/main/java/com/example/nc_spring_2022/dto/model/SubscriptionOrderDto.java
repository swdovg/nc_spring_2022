package com.example.nc_spring_2022.dto.model;

import lombok.Data;

import java.util.Date;

@Data
public class SubscriptionOrderDto {
    private Long orderId;
    private SubscriptionDto subscription;
    private Date date;
}
