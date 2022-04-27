package com.example.nc_spring_2022.security.oauth2.user;

import com.example.nc_spring_2022.exception.OAuth2AuthenticationProcessingException;
import com.example.nc_spring_2022.model.AuthProvider;

import java.util.Map;

public class OAuth2UserFactory {
    private OAuth2UserFactory() {
        throw new IllegalStateException();
    }

    public static OAuth2User getOAuth2User(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.toString())) {
            return new GoogleOAuth2User(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
