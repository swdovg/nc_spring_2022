package com.example.nc_spring_2022.dto.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Data
public class RegisterDto {
    @Length(min = 5, message = "Email must contain at least 5 symbols")
    private final String email;
    @Length(min = 8, message = "Password must contain at least 8 symbols")
    private final String password;
    @Pattern(regexp = "^((\\+7|7|8)+([0-9]){10})$", message = "Wrong phone number")
    private final String phoneNumber;
    private final String name;
    private final boolean isConsumer;
}
