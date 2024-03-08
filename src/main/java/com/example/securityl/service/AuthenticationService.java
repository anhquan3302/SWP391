package com.example.securityl.service;

import com.example.securityl.dto.request.UserRequest.AuthenticationRequest;
import com.example.securityl.dto.request.UserRequest.RegisterRequest;
import com.example.securityl.dto.request.response.UserResponse.AuthenticationResponse;
import com.example.securityl.dto.request.response.UserResponse.RegisterResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    RegisterResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
