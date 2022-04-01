package com.example.nc_spring_2022.controller;

import com.example.nc_spring_2022.dto.LoginDto;
import com.example.nc_spring_2022.dto.RegisterDto;
import com.example.nc_spring_2022.dto.Response;
import com.example.nc_spring_2022.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/consumer/login")
    public Response<Map<String, String>> loginConsumer(@Valid @RequestBody LoginDto loginDto) {
        return new Response<>(authService.login(loginDto));
    }

    @PostMapping("/consumer/register")
    public Response<Map<String, String>> registerConsumer(@Valid @RequestBody RegisterDto registerDto) {
        return new Response<>(authService.register(registerDto));
    }

    @PostMapping("/supplier/login")
    public Response<Map<String, String>> loginSupplier(@Valid @RequestBody LoginDto loginDto) {
        return new Response<>(authService.login(loginDto));
    }

    @PostMapping("/supplier/register")
    public Response<Map<String, String>> registerSupplier(@Valid @RequestBody RegisterDto registerDto) {
        return new Response<>(authService.register(registerDto));
    }
}
