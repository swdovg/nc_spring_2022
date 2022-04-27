package com.example.nc_spring_2022.security.jwt;

import com.example.nc_spring_2022.model.Role;
import com.example.nc_spring_2022.model.User;

public class JwtUserFactory {
    private JwtUserFactory() {
        throw new IllegalStateException();
    }

    public static JwtUser create(User user) {
        JwtUser jwtUser = new JwtUser();

        jwtUser.setId(user.getId());
        jwtUser.setUsername(user.getEmail());
        jwtUser.setPassword(user.getPassword());
        jwtUser.setRole(user.getRole());
        jwtUser.setVersion(user.getVersion());

        return jwtUser;
    }

    public static JwtUser create(Long id, Role role, Integer version) {
        JwtUser jwtUser = new JwtUser();

        jwtUser.setId(id);
        jwtUser.setRole(role);
        jwtUser.setVersion(version);

        return jwtUser;
    }
}
