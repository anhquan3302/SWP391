package com.example.securityl.controller;

import com.example.securityl.request.UserRequest.AuthenticationRequest;
import com.example.securityl.request.UserRequest.RegisterRequest;
import com.example.securityl.response.UserResponse.AuthenticationResponse;
import com.example.securityl.response.UserResponse.RegisterResponse;
import com.example.securityl.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(service.register(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(RegisterResponse
                    .builder()
                    .status(e.getMessage())
                    .message("Register fail")
                    .build());
        }


    }

    @PostMapping("/authenticate")

    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(service.authenticate(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/refreshtoken")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {
        service.refreshToken(request, response);
    }


}
