package com.example.nc_spring_2022.security.oauth2;

import com.example.nc_spring_2022.security.oauth2.user.OAuth2User;
import com.example.nc_spring_2022.security.oauth2.user.OAuth2UserFactory;
import com.example.nc_spring_2022.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final AuthService authService;
    private final String redirectPath = "/";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        addCookies(response, authentication);

        response.sendRedirect(redirectPath);
    }

    private void addCookies(HttpServletResponse response, Authentication authentication) {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = OAuth2UserFactory.getOAuth2User(authenticationToken.getAuthorizedClientRegistrationId(),
                authenticationToken.getPrincipal().getAttributes());

        Map<String, String> userData = authService.registerOrUpdateOAuthUser(oAuth2User);
        Cookie token = new Cookie("token", userData.get("token"));
        token.setPath(redirectPath);
        Cookie userId = new Cookie("userId", userData.get("userId"));
        userId.setPath(redirectPath);
        Cookie role = new Cookie("role", userData.get("role"));
        role.setPath(redirectPath);

        response.addCookie(token);
        response.addCookie(userId);
        response.addCookie(role);
    }
}
