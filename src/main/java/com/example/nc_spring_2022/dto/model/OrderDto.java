package com.example.nc_spring_2022.dto.model;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private UserDto consumer;
    private Long subscriptionId;
    private String subscriptionName;
}
