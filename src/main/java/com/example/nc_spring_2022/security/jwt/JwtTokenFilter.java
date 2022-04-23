package com.example.nc_spring_2022.security.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends BasicAuthenticationFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUserDetailsService jwtUserDetailsService;
    private HttpServletRequest servletRequest;
    private HttpServletResponse servletResponse;
    private FilterChain filterChain;
    private String token;
    private String requestUrl;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider,
                          JwtUserDetailsService jwtUserDetailsService,
                          AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest,
                                    HttpServletResponse servletResponse,
                                    FilterChain filterChain) throws IOException, ServletException {
        this.servletRequest = servletRequest;
        this.servletResponse = servletResponse;
        this.filterChain = filterChain;

        token = jwtTokenProvider.resolveToken(servletRequest);
        requestUrl = servletRequest.getRequestURL().toString();

        if (isRefreshRequestNotContainsToken()) {
            rejectRequest();
        } else if (isAuthenticationRequest()) {
            acceptRequest();
        } else {
            setAuthentication();
            acceptRequest();
        }
    }

    private boolean isRefreshRequestNotContainsToken() {
        return token == null && requestUrl.contains("refreshToken");
    }

    private boolean isAuthenticationRequest() {
        return token == null || requestUrl.contains("refreshToken") || !jwtTokenProvider.verifyToken(token);
    }

    private void acceptRequest() throws ServletException, IOException {
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void rejectRequest() throws IOException {
        servletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    private void setAuthentication() throws IOException {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        verifyJwtUserValid(jwtUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void verifyJwtUserValid(JwtUser jwtUser) throws IOException {
        JwtUser dbUser = getDbUser(jwtUser.getId());

        if (dbUser == null || !jwtUser.getVersion().equals(dbUser.getVersion())) {
            rejectRequest();
        }
    }

    private JwtUser getDbUser(Long userId) throws IOException {
        try {
            return (JwtUser) jwtUserDetailsService.loadUserById(userId);
        } catch (EntityNotFoundException exception) {
            rejectRequest();
            return null;
        }
    }
}
