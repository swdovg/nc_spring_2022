package com.example.nc_spring_2022.dto.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationDto {
    private Long id;
    private String location;
    private Long userId;
}
