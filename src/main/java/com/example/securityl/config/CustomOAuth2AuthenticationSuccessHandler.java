package com.example.securityl.config;

import com.example.securityl.entity.Enum.Role;
import com.example.securityl.entity.User;
import com.example.securityl.repository.UserRepository;
import com.example.securityl.response.AuthenticationResponse;
import com.example.securityl.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomOAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public CustomOAuth2AuthenticationSuccessHandler(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");
            String address = oAuth2User.getAttribute("address");

            User existingUser = userRepository.findByEmail(email).orElse(null);
            if (existingUser == null) {
                User newUser = User.builder()
                        .name(name)
                        .email(email)
                        .address(address)
                        .role(Role.USER)
                        .build();

                User savedUser = userRepository.save(newUser);
                String jwtToken = jwtService.generateToken(savedUser);

                AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                        .staus("Success")
                        .messages("Register success")
                        .token(jwtToken)
                        .build();

                response.getWriter().write(new ObjectMapper().writeValueAsString(authenticationResponse));
                response.setStatus(HttpServletResponse.SC_OK);
            } else {

                String jwtToken = jwtService.generateToken(existingUser);

                AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                        .staus("Success")
                        .messages("Login success")
                        .token(jwtToken)
                        .build();

                response.getWriter().write(new ObjectMapper().writeValueAsString(authenticationResponse));
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }
}
