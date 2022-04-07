package com.example.nc_spring_2022.dto.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    @Length(min = 4, message = "Name of category must be at least 4 symbols")
    private String name;
    @PositiveOrZero
    private Long parentId;
}
