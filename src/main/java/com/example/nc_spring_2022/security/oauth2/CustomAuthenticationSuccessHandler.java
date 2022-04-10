package com.example.nc_spring_2022.security.oauth2;

import com.example.nc_spring_2022.security.oauth2.user.OAuth2User;
import com.example.nc_spring_2022.security.oauth2.user.OAuth2UserFactory;
import com.example.nc_spring_2022.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = OAuth2UserFactory.getOAuth2User(authenticationToken.getAuthorizedClientRegistrationId(),
                authenticationToken.getPrincipal().getAttributes());

        Map<String, String> token = authService.registerOrUpdateOAuthUser(oAuth2User);
        response.sendRedirect("/?token=" + token.get("token"));
    }
}
