package com.example.securityl.config;

import com.example.securityl.model.Enum.Role;
import com.example.securityl.model.User;
import com.example.securityl.repository.UserRepository;
import com.example.securityl.response.UserResponse.AuthenticationResponse;
import com.example.securityl.serviceimpl.JwtServiceImpl;
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
    private final JwtServiceImpl jwtService;

    @Autowired
    public CustomOAuth2AuthenticationSuccessHandler(UserRepository userRepository, JwtServiceImpl jwtService) {
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
            String accessToken;
            String refreshToken;
            if (existingUser == null) {
                User newUser = User.builder()
                        .name(name)
                        .email(email)
                        .address(address)
                        .role(Role.user)
                        .status(true)
                        .build();
                User savedUser = userRepository.save(newUser);
                accessToken = jwtService.generateToken(savedUser);
                refreshToken = jwtService.generateRefeshToken(savedUser);
            } else {
                accessToken = jwtService.generateToken(existingUser);
                refreshToken = jwtService.generateRefeshToken(existingUser);
            }
            sendAuthenticationResponse(response, "Success", "Login success", accessToken, refreshToken);
        }
    }


    private void sendAuthenticationResponse(HttpServletResponse response, String status, String message, String accessToken, String refreshToken) throws IOException {
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .status(status)
                .messages(message)
                .token(accessToken)
                .refeshToken(refreshToken)
                .build();
        // Gửi thông tin đăng nhập thành công và token dưới dạng JSON
        response.getWriter().write(new ObjectMapper().writeValueAsString(authenticationResponse));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
