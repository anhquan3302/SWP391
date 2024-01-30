package com.example.securityl.service;

import com.example.securityl.entity.User;
import com.example.securityl.request.AuthenticationRequest;
import com.example.securityl.request.RegisterRequest;
import com.example.securityl.response.AuthenticationResponse;
import com.example.securityl.response.RegisterResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    RegisterResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
