package com.example.nc_spring_2022.dto.model;

import com.example.nc_spring_2022.model.Currency;
import com.example.nc_spring_2022.model.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private Currency currency;
    private LocationDto defaultLocation;
    private Role role;
}
