package com.example.nc_spring_2022.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app")
public class Properties {
    private Auth auth;

    @Getter
    @Setter
    public static class Auth {
        private String tokenSecret;
        private Long tokenExpirationMSec;
        private String tokenPrefix;
        private String headerString;
    }
}
