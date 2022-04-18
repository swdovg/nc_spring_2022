package com.example.nc_spring_2022.controller;

import com.example.nc_spring_2022.dto.model.*;
import com.example.nc_spring_2022.model.User;
import com.example.nc_spring_2022.service.AuthService;
import com.example.nc_spring_2022.service.LocationService;
import com.example.nc_spring_2022.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final LocationService locationService;
    private final AuthService authService;

    @PutMapping("/currency")
    public Response<UserDto> updateCurrency(@RequestBody RequestDto requestDto) {
        return new Response<>(userService.updateCurrency(requestDto));
    }

    @PutMapping("/name")
    public Response<UserDto> updateName(@RequestBody RequestDto requestDto) {
        return new Response<>(userService.updateName(requestDto));
    }

    @PutMapping("/password")
    public Response<Map<String, String>> updatePassword(@RequestBody PasswordChangeDto passwordChangeDto) {
        return new Response<>(authService.updatePassword(passwordChangeDto));
    }

    @PutMapping("/location")
    public Response<UserDto> updateDefaultLocation(@RequestBody LocationDto newDefaultLocation) {
        return new Response<>(userService.updateDefaultLocation(newDefaultLocation));
    }

    @GetMapping("/location")
    public Response<List<LocationDto>> getLocations() {
        return new Response<>(locationService.getDtosByUserId());
    }

    @GetMapping
    public Response<UserDto> getUser() {
        return new Response<>(userService.getUserDto());
    }

    @DeleteMapping
    public Response<String> deleteUser(@Valid @RequestBody User user) {
        userService.delete(user);
        return new Response<>("User was successfully deleted");
    }
}
