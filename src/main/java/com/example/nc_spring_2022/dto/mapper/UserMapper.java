package com.example.nc_spring_2022.dto.mapper;

import com.example.nc_spring_2022.dto.model.UserDto;
import com.example.nc_spring_2022.model.User;
import com.example.nc_spring_2022.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final LocationMapper locationMapper;
    private final UserRepository userRepository;

    public UserDto createFrom(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setCurrency(user.getCurrency());
        if (user.getDefaultLocation() != null) {
            userDto.setDefaultLocation(locationMapper.createFrom(user.getDefaultLocation()));
        } else {
            userDto.setDefaultLocation(null);
        }
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setRole(user.getRole());

        return userDto;
    }

    public User createFrom(UserDto userDto) {
        return userRepository.findById(userDto.getId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with id: %d does not exists", userDto.getId())));
    }
}
