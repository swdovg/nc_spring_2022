package com.example.nc_spring_2022.security;

import com.example.nc_spring_2022.exception.WrongCredentialsException;
import com.example.nc_spring_2022.security.jwt.JwtUser;
import com.example.nc_spring_2022.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFacade {
    private final UserService userService;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            JwtUser jwtUser = (JwtUser) auth.getPrincipal();
            if (userService.isUserExists(jwtUser.getId())) {
                return jwtUser.getId();
            }
        }
        throw new WrongCredentialsException("Forbidden");
    }
}
