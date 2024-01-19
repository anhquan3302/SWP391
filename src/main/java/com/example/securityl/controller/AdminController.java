package com.example.securityl.controller;


import com.example.securityl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    @PreAuthorize("hasAuthority('admin:read')")
    public String Home(){
        return "Hello world";
    }




    @PostMapping()
    @PreAuthorize("hasAuthority('admin:create')")
    public String post(){
        return "Post: admin controller";
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public String put(){
        return "Put: admin controller";
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    public String delete(){
        return "Delete: Admin controller";
    }
}
