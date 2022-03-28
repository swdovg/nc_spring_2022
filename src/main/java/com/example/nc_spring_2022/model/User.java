package com.example.nc_spring_2022.model;

public interface User {
    Long getId();

    String getEmail();

    String getPassword();

    Role getRole();

    void setRole(Role role);

    void setEmail(String email);

    void setPassword(String password);
}
