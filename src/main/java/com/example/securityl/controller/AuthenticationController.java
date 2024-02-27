package com.example.securityl.controller;

import com.example.securityl.model.User;
import com.example.securityl.request.UserRequest.AuthenticationRequest;
import com.example.securityl.request.UserRequest.RegisterRequest;
import com.example.securityl.response.UserResponse.AuthenticationResponse;
import com.example.securityl.response.UserResponse.RegisterResponse;
import com.example.securityl.service.AuthenticationService;
import com.example.securityl.service.EmailService;
import com.example.securityl.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER','ADMIN','STAFF')")
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

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

    @PostMapping("/forgetpass")
    public ResponseEntity<?> register(@RequestParam String email) {
        try {
            User user = userService.findByEmailForMail(email);
            if(user == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
            }
            else {
                String pass = RandomStringUtils.randomAlphanumeric(6);

                user.setPassword(passwordEncoder.encode(pass));
                user = userService.saveUserForMail(user);
                emailService.sendSimpleMessage(email,"Reset password","New password is : " + pass);
                return ResponseEntity.ok(user);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(RegisterResponse
                    .builder()
                    .status(e.getMessage())
                    .message("Register fail")
                    .build());
        }


    }

}
