package com.example.nc_spring_2022.security.jwt;

import com.example.nc_spring_2022.exception.JwtAuthenticationException;
import com.example.nc_spring_2022.model.User;
import com.example.nc_spring_2022.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends BasicAuthenticationFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private ServletResponse servletResponse;
    private ServletRequest servletRequest;
    private FilterChain filterChain;
    private String token;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider,
                          UserService userService,
                          AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest,
                                    HttpServletResponse servletResponse,
                                    FilterChain filterChain)
            throws IOException, ServletException {
        this.servletRequest = servletRequest;
        this.servletResponse = servletResponse;
        this.filterChain = filterChain;
        token = jwtTokenProvider.resolveToken(servletRequest);

        if (token == null) {
            acceptRequest();
            return;
        }

        String requestUrl = servletRequest.getRequestURL().toString();
        if (requestUrl.contains("refreshToken")) {
            acceptRequest();
            return;
        }

        verifyToken();
        setAuthentication();
        acceptRequest();
    }

    private void setAuthentication() throws IOException {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        verifyJwtUserValid(jwtUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void verifyToken() throws IOException {
        try {
            jwtTokenProvider.verifyToken(token);
        } catch (JwtAuthenticationException exception) {
            rejectRequest();
        }
    }

    private void verifyJwtUserValid(JwtUser jwtUser) throws IOException {
        User dbUser = getDbUser(jwtUser.getId());

        if (dbUser == null || !jwtUser.getVersion().equals(dbUser.getVersion())) {
            rejectRequest();
        }
    }

    private User getDbUser(Long userId) throws IOException {
        try {
            return userService.findById(userId);
        } catch (EntityNotFoundException exception) {
            rejectRequest();
        }

        return null;
    }

    private void acceptRequest() throws ServletException, IOException {
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void rejectRequest() throws IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid session, please log in again");
    }
}
