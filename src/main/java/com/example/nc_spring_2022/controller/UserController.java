package com.example.nc_spring_2022.controller;

import com.example.nc_spring_2022.dto.PasswordChangeDto;
import com.example.nc_spring_2022.dto.Response;
import com.example.nc_spring_2022.dto.UserDto;
import com.example.nc_spring_2022.model.Currency;
import com.example.nc_spring_2022.model.Location;
import com.example.nc_spring_2022.model.User;
import com.example.nc_spring_2022.security.AuthenticationFacade;
import com.example.nc_spring_2022.service.LocationService;
import com.example.nc_spring_2022.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    @PutMapping("/currency")
    public Response<UserDto> updateCurrency(@RequestBody Currency newCurrency) {
        User user = userService.updateCurrency(newCurrency);
        return new Response<>(userToUserDto(user));
    }

    @PutMapping("/name")
    public Response<UserDto> updateName(@RequestBody String newName) {
        User user = userService.updateName(newName);
        return new Response<>(userToUserDto(user));
    }

    @PutMapping("/password")
    public Response<String> updatePassword(@RequestBody PasswordChangeDto passwordChangeDto) {
        userService.updatePassword(passwordChangeDto);
        return new Response<>("Password was successfully changed");
    }

    @PutMapping("/location")
    public Response<UserDto> updateDefaultLocation(@RequestBody Location newDefaultLocation) {
        User user = userService.updateDefaultLocation(newDefaultLocation);
        return new Response<>(userToUserDto(user));
    }

    @GetMapping("/location")
    public Response<List<Location>> getLocations() {
        Long userId = authenticationFacade.getUserId();
        return new Response<>(locationService.findByUserId(userId));
    }

    @DeleteMapping
    public Response<String> deleteUser(@Valid @RequestBody User user) {
        userService.delete(user);
        return new Response<>("User was successfully deleted");
    }

    private UserDto userToUserDto(User user) {
        return objectMapper.convertValue(user, UserDto.class);
    }
}
