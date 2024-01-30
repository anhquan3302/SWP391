package com.example.securityl.controller;


import com.example.securityl.request.CreateUserRequest;
import com.example.securityl.request.UpdateUserRequest;
import com.example.securityl.response.*;
import com.example.securityl.serviceimpl.UserServiceimpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
public class AdminController {
    private final UserServiceimpl userService;


    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('admin:read')")
    private ResponseEntity<ResponseUser> getAll() {
        return userService.findAllUser();
    }


    @PostMapping("/createUser")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<CreateResponse> create(@RequestBody CreateUserRequest request) {
        try {
            return ResponseEntity.ok(userService.createUser(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(CreateResponse.builder()
                    .status("Create staff fail")
                    .message(e.getMessage())
                    .build());
        }
    }

    @PutMapping("/updateUser/{email}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<UpdateUserResponse> updateStaff(
            @PathVariable String email,
            @RequestBody UpdateUserRequest updateUserRequest) {
        try {
            return ResponseEntity.ok(userService.updateUser(email, updateUserRequest).getBody());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(UpdateUserResponse.builder()
                    .status("Update fail")
                    .message(e.getMessage())
                    .build());
        }
    }

    @DeleteMapping("/delete/{email}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<DeleteResponse> deleteUser(
            @PathVariable String email) {
        try {
            return ResponseEntity.ok(userService.deleteUser(email).getBody());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(DeleteResponse.builder()
                    .status("Delete fail")
                    .message(e.getMessage())
                    .build());
        }
    }

//    @GetMapping("/findAll/{email}")
//    @PreAuthorize("hasAuthority('admin:read')")
//    public ResponseEntity<ResponseUser> getAll(@PathVariable String email) {
//        try {
//            List<User> list = userService.getList(email);
//            return ResponseEntity.ok(ResponseUser.builder()
//                    .status("Success")
//                    .message("Get All User")
//                    .userList(list)
//                    .build());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(ResponseUser.builder()
//                    .status("Fail")
//                    .message(e.getMessage())
//                    .userList(null)
//                    .build());
//        }
//    }

    @GetMapping("/findUserById/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<ResponseObject> geUserById(@PathVariable Integer id) {
        try {
            var user = userService.getUser(id);
            return ResponseEntity.ok(ResponseObject.builder()
                    .status("Success")
                    .message("Find user")
                    .user(user)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .status("Fail")
                    .message(e.getMessage())
                    .user(null)
                    .build());
        }

    }


}
