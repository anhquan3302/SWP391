package com.example.securityl.service;

import com.example.securityl.request.UserRequest.AuthenticationRequest;
import com.example.securityl.request.UserRequest.RegisterRequest;
import com.example.securityl.response.UserResponse.AuthenticationResponse;
import com.example.securityl.response.UserResponse.RegisterResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    RegisterResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
