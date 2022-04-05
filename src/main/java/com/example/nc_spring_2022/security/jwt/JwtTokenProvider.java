package com.example.nc_spring_2022.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.nc_spring_2022.config.Properties;
import com.example.nc_spring_2022.exception.JwtAuthenticationException;
import com.example.nc_spring_2022.model.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    private final Properties properties;
    private Algorithm algorithm;

    public JwtTokenProvider(Properties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(properties.getAuth().getTokenSecret());
    }

    public String createToken(Long id, Role role, Integer version) {
        Date expirationDate = new Date(new Date().getTime() + properties.getAuth().getTokenExpirationMSec());

        Map<String, String> claims = new HashMap<>();
        claims.put("id", id.toString());
        claims.put("role", role.toString());
        claims.put("version", version.toString());

        return JWT.create()
                .withPayload(claims)
                .withIssuer("nc")
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }

    public String refreshToken(String token) {
        Map<String, Claim> claims = getClaims(token);
        Map<String, String> stringClaims = new HashMap<>();
        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            if (stringClaims.put(entry.getKey(), entry.getValue().asString()) != null) {
                throw new IllegalStateException("Duplicate key");
            }
        }
        Date expirationDate = new Date(new Date().getTime() + properties.getAuth().getTokenExpirationMSec());

        return JWT.create()
                .withPayload(stringClaims)
                .withIssuer("nc")
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(properties.getAuth().getHeaderString());
        if (bearerToken != null && bearerToken.startsWith(properties.getAuth().getTokenPrefix())) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("nc")
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            throw new JwtAuthenticationException("Invalid session, please log in again");
        }
    }

    public Map<String, Claim> getClaims(String token) {
        return JWT.decode(token).getClaims();
    }

    public Long getUserId(Map<String, Claim> claims) {
        return Long.parseLong(claims.get("id").asString());
    }

    public Role getUserRole(Map<String, Claim> claims) {
        return claims.get("role").as(Role.class);
    }

    public Integer getUserVersion(Map<String, Claim> claims) {
        return Integer.parseInt(claims.get("version").asString());
    }

    public Authentication getAuthentication(String token) {
        Map<String, Claim> claims = getClaims(token);
        JwtUser userDetails = JwtUserFactory.create(getUserId(claims), getUserRole(claims), getUserVersion(claims));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
