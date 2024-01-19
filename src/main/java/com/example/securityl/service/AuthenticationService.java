package com.example.securityl.service;

import com.example.securityl.entity.Role;
import com.example.securityl.entity.Token;
import com.example.securityl.entity.TokenType;
import com.example.securityl.entity.User;
import com.example.securityl.repository.TokenRepository;
import com.example.securityl.repository.UserRepository;
import com.example.securityl.request.AuthenticationRequest;
import com.example.securityl.request.RegisterRequest;
import com.example.securityl.response.AuthenticationResponse;
import com.example.securityl.response.LoginGoogleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var saveUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefeshToken(user);
        saveToken(saveUser, jwtToken);
        return AuthenticationResponse.builder()
                .staus("Success")
                .messages("Register success")
                .token(jwtToken)
                .refeshToken(refreshToken)
                .build();

    }

    private void saveToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUsserTokens(User user) {
        var vaildUserToken = tokenRepository.findAllVaildTokenByUser(user.getUserId());
        if (vaildUserToken.isEmpty())
            return;
        vaildUserToken.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefeshToken(user);
        revokeAllUsserTokens(user);
        saveToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .staus("Success")
                .messages("Login success")
                .token(jwtToken)
                .refeshToken(refreshToken)
                .build();
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var newToken = jwtService.generateToken(user);
                revokeAllUsserTokens(user);
                saveToken(user, newToken);
                var authResponse = AuthenticationResponse.builder()
                        .staus("Success")
                        .messages("New token")
                        .token(newToken)
                        .refeshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                ;
            }
        }
    }



}
