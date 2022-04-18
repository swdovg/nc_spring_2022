package com.example.nc_spring_2022.dto.model;

import lombok.Data;

@Data
public class ImageDto {
    private Long id;
    private Long relatedEntityId;
    private String name;
    private String type;
}
