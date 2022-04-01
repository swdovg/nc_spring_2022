package com.example.nc_spring_2022.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class LoginDto {
    @Length(min = 5, message = "Email must contain at least 5 symbols")
    private final String email;
    @Length(min = 8, message = "Password must contain at least 8 symbols")
    private final String password;
}
