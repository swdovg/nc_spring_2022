package com.example.nc_spring_2022.security.oauth2.user;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public abstract class OAuth2User {
    protected final Map<String, Object> attributes;

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();
}
