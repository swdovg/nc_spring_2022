package com.example.nc_spring_2022.security.jwt;

import com.example.nc_spring_2022.model.User;
import com.example.nc_spring_2022.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);

        return JwtUserFactory.create(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = userService.findById(id);

        return JwtUserFactory.create(user);
    }
}
