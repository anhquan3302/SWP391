package com.example.securityl.controller;


import com.example.securityl.request.UserRequest.SearchRequest;
import com.example.securityl.response.ProductResponse.ResponseObject;
import com.example.securityl.response.UserResponse.ResponseUser;
import com.example.securityl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class UserController {
    @Autowired
    UserService userService;
//    @GetMapping("/getUsers")
//    public ResponseEntity<ResponseUser> getAllUsers(@RequestBody SearchRequest req){
//        return userService.searchUsers(req);
//    }
}
