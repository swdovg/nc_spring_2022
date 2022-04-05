package com.example.nc_spring_2022.dto.mapper;

import com.example.nc_spring_2022.dto.model.LocationDto;
import com.example.nc_spring_2022.dto.model.UserDto;
import com.example.nc_spring_2022.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final LocationMapper locationMapper;

    public UserDto createFrom(User user) {
        LocationDto locationDto = locationMapper.createFrom(user.getDefaultLocation());
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setCurrency(user.getCurrency());
        userDto.setDefaultLocation(locationDto);
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setRole(user.getRole());
        return userDto;
    }
}
