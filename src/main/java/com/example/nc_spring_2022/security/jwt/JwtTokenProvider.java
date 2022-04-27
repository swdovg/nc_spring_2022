package com.example.nc_spring_2022.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.nc_spring_2022.config.SecurityProperties;
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
    private final SecurityProperties securityProperties;
    private Algorithm algorithm;

    public JwtTokenProvider(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(securityProperties.getAuth().getTokenSecret());
    }

    public String createToken(JwtUser jwtUser) {
        Date expirationDate = new Date(new Date().getTime() + securityProperties.getAuth().getTokenExpirationMSec());

        Map<String, String> claims = new HashMap<>();
        claims.put("id", jwtUser.getId().toString());
        claims.put("role", jwtUser.getRole().toString());
        claims.put("version", jwtUser.getVersion().toString());

        return JWT.create()
                .withPayload(claims)
                .withIssuer("nc")
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(securityProperties.getAuth().getHeaderString());
        if (bearerToken != null && bearerToken.startsWith(securityProperties.getAuth().getTokenPrefix())) {
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
            return false;
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

    public JwtUser getJwtUser(String token) {
        Map<String, Claim> claims = getClaims(token);
        return JwtUserFactory.create(getUserId(claims), getUserRole(claims), getUserVersion(claims));
    }

    public Authentication getAuthentication(String token) {
        JwtUser userDetails = getJwtUser(token);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
