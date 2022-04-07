package com.example.nc_spring_2022.security.jwt;

import com.example.nc_spring_2022.model.Role;
import com.example.nc_spring_2022.model.User;

public class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(), user.getEmail(), user.getPassword(), user.getRole(), user.getVersion());
    }

    public static JwtUser create(Long id, Role role, Integer version) {
        return new JwtUser(id, null, null, role, version);
    }
}
