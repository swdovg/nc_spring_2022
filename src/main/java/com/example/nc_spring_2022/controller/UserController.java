package com.example.nc_spring_2022.controller;

import com.example.nc_spring_2022.dto.mapper.LocationMapper;
import com.example.nc_spring_2022.dto.mapper.UserMapper;
import com.example.nc_spring_2022.dto.model.LocationDto;
import com.example.nc_spring_2022.dto.model.PasswordChangeDto;
import com.example.nc_spring_2022.dto.model.Response;
import com.example.nc_spring_2022.dto.model.UserDto;
import com.example.nc_spring_2022.model.Currency;
import com.example.nc_spring_2022.model.Location;
import com.example.nc_spring_2022.model.User;
import com.example.nc_spring_2022.security.AuthenticationFacade;
import com.example.nc_spring_2022.service.LocationService;
import com.example.nc_spring_2022.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final LocationService locationService;
    private final AuthenticationFacade authenticationFacade;
    private final UserMapper userMapper;
    private final LocationMapper locationMapper;

    @PutMapping("/currency")
    public Response<UserDto> updateCurrency(@RequestBody Currency newCurrency) {
        User user = userService.updateCurrency(newCurrency);
        return new Response<>(userMapper.createFrom(user));
    }

    @PutMapping("/name")
    public Response<UserDto> updateName(@RequestBody String newName) {
        User user = userService.updateName(newName);
        return new Response<>(userMapper.createFrom(user));
    }

    @PutMapping("/password")
    public Response<String> updatePassword(@RequestBody PasswordChangeDto passwordChangeDto) {
        userService.updatePassword(passwordChangeDto);
        return new Response<>("Password was successfully changed");
    }

    @PutMapping("/location")
    public Response<UserDto> updateDefaultLocation(@RequestBody LocationDto newDefaultLocation) {
        User user = userService.updateDefaultLocation(newDefaultLocation);
        return new Response<>(userMapper.createFrom(user));
    }

    @GetMapping("/location")
    public Response<List<LocationDto>> getLocations() {
        Long userId = authenticationFacade.getUserId();
        List<Location> locations = locationService.findByUserId(userId);
        return new Response<>(locationMapper.createFrom(locations));
    }

    @GetMapping
    public Response<UserDto> getUser() {
        return new Response<>(userMapper.createFrom(userService.getUser()));
    }

    @DeleteMapping
    public Response<String> deleteUser(@Valid @RequestBody User user) {
        userService.delete(user);
        return new Response<>("User was successfully deleted");
    }
}
