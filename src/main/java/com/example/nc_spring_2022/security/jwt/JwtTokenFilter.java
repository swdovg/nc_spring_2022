package com.example.nc_spring_2022.security.jwt;

import com.example.nc_spring_2022.exception.JwtAuthenticationException;
import com.example.nc_spring_2022.model.User;
import com.example.nc_spring_2022.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURL = request.getRequestURL().toString();
        String token;
        try {
            token = jwtTokenProvider.resolveToken(request);

            if (token != null && jwtTokenProvider.verifyToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                JwtUser tokenUser = (JwtUser) authentication.getPrincipal();
                User dbUser = userService.findById(tokenUser.getId());

                if (dbUser != null && tokenUser.getVersion().equals(dbUser.getVersion())) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    rejectRequest(servletResponse);
                }
            }
            if (requestURL.contains("refreshToken") && token == null) {
                rejectRequest(servletResponse);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } catch (JwtAuthenticationException e) {
            if (requestURL.contains("refreshToken")) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                rejectRequest(servletResponse);
            }
        } catch (EntityNotFoundException e) {
            rejectRequest(servletResponse);
        }
    }

    private void rejectRequest(ServletResponse servletResponse) throws IOException {
        HttpServletResponse resp = ((HttpServletResponse) servletResponse);
        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid session, please log in again");
    }
}
