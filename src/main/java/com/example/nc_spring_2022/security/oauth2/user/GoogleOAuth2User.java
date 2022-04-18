package com.example.nc_spring_2022.security.oauth2.user;

import java.util.Map;

public class GoogleOAuth2User extends OAuth2User {
    public GoogleOAuth2User(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}
