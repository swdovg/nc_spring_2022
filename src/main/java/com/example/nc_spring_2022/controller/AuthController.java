package com.example.nc_spring_2022.controller;

import com.example.nc_spring_2022.dto.model.LoginDto;
import com.example.nc_spring_2022.dto.model.RegisterDto;
import com.example.nc_spring_2022.dto.model.Response;
import com.example.nc_spring_2022.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public Response<Map<String, String>> loginSupplier(@Valid @RequestBody LoginDto loginDto) {
        return new Response<>(authService.login(loginDto));
    }

    @PostMapping("/register")
    public Response<Map<String, String>> registerSupplier(@Valid @RequestBody RegisterDto registerDto) {
        return new Response<>(authService.register(registerDto));
    }

    @GetMapping("/refreshToken")
    public Response<Map<String, String>> refreshToken(HttpServletRequest request) {
        return new Response<>(authService.refreshToken(request));
    }
}
