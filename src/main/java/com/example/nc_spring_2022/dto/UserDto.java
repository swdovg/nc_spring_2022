package com.example.nc_spring_2022.dto;

import com.example.nc_spring_2022.model.Currency;
import com.example.nc_spring_2022.model.Location;
import com.example.nc_spring_2022.model.Role;
import lombok.Data;

@Data
public class UserDto {
    private final Long id;
    private final String name;
    private final String phoneNumber;
    private final String email;
    private final Currency currency;
    private final Location defaultLocation;
    private final Role role;
}
