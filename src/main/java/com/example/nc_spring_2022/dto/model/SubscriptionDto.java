package com.example.nc_spring_2022.dto.model;

import com.example.nc_spring_2022.model.Currency;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
public class SubscriptionDto {
    private Long id;
    @Length(min = 4, message = "Title must have at least 4 symbols")
    private String title;
    private String description;
    @PositiveOrZero
    private Long price;
    private Currency currency;
    @PositiveOrZero
    private Double averageRating;
    private CategoryDto category;
    private UserDto supplier;
    private boolean isOrdered = false;
}
