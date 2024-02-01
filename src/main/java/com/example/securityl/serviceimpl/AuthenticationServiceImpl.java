package com.example.securityl.serviceimpl;

import com.example.securityl.entity.Enum.Role;
import com.example.securityl.entity.Token;
import com.example.securityl.entity.Enum.TokenType;
import com.example.securityl.entity.User;
import com.example.securityl.repository.TokenRepository;
import com.example.securityl.repository.UserRepository;
import com.example.securityl.request.UserRequest.AuthenticationRequest;
import com.example.securityl.request.UserRequest.RegisterRequest;
import com.example.securityl.response.UserResponse.AuthenticationResponse;
import com.example.securityl.response.UserResponse.RegisterResponse;
import com.example.securityl.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    Pattern pattern = Pattern.compile(emailRegex);

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (!(request.getPhone().length() == 10)) {
            return RegisterResponse
                    .builder()
                    .status("Register fail")
                    .message("Phone is not valid")
                    .build();
        }
        Matcher matcher = pattern.matcher(request.getEmail());
        if (!matcher.matches()) {
            return RegisterResponse
                    .builder()
                    .status("Register fail")
                    .message("Email is not valid")
                    .build();
        }
        var user = User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .status(true)
                .build();
        var existedEmail = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (existedEmail == null) {
            userRepository.save(user);
            return RegisterResponse.builder()
                    .status("Success")
                    .message("Register success")
                    .build();
        } else {
            return RegisterResponse.builder()
                    .status("Register fail")
                    .message("Account existed")
                    .build();
        }
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

    @Override
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

    @Override
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
