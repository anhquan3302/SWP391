package com.example.securityl.controller;

import com.example.securityl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class UserController {
    private final UserService service;









}
