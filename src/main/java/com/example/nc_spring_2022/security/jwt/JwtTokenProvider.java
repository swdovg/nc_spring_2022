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

    public String createToken(Long id, Role role) {
        Map<String, String> claims = new HashMap<>();
        claims.put("id", id.toString());
        claims.put("role", role.toString());

        return JWT.create()
                .withPayload(claims)
                .withIssuer("nc")
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
            throw new JwtAuthenticationException("Your session has expired, please log in again");
        }
    }

    private Map<String, Claim> getClaims(String token) {
        return JWT.decode(token).getClaims();
    }

    private Long getUserId(Map<String, Claim> claims) {
        return claims.get("id").asLong();
    }

    private Role getUserRole(Map<String, Claim> claims) {
        return claims.get("role").as(Role.class);
    }

    public Authentication getAuthentication(String token) {
        Map<String, Claim> claims = getClaims(token);
        JwtUser userDetails = JwtUserFactory.create(getUserId(claims), getUserRole(claims));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
