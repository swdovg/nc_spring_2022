package com.example.nc_spring_2022.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@RequiredArgsConstructor
public class PasswordChangeDto {
    private final String oldPassword;
    @Length(min = 8, message = "Password must contain at least 8 symbols")
    private final String newPassword;
}
