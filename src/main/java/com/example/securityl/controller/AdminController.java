package com.example.securityl.controller;


import com.example.securityl.model.User;
import com.example.securityl.request.UserRequest.UserRequest;
import com.example.securityl.request.UserRequest.SearchRequest;
import com.example.securityl.request.UserRequest.UpdateUserRequest;
import com.example.securityl.response.UserResponse.*;
import com.example.securityl.serviceimpl.UserServiceimpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
public class AdminController {
    private final UserServiceimpl userService;


    private static final Logger logger = Logger.getLogger(ShoppingController.class.getName());

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('admin:read')")
    private ResponseEntity<ResponseUser> getAll() {
        return userService.findAllUser();
    }


    @PostMapping("/createUser")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<CreateResponse> create(@RequestBody UserRequest request) {
        try {
            return ResponseEntity.ok(userService.createUser(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(CreateResponse.builder()
                    .status("Create staff fail")
                    .message(e.getMessage())
                    .userResponse(null)
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
            UserResponse user = userService.getUser(id);
            return ResponseEntity.ok(ResponseObject.builder()
                    .status("Success")
                    .message("Find user")
                    .object(user)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .status("Fail")
                    .message(e.getMessage())
                    .object(null)
                    .build());
        }

    }

    @GetMapping("/getUsers")
    public ResponseEntity<ResponseUser> getAllUsers(@RequestBody SearchRequest req) {
        return userService.searchUsers(req);
    }

    @GetMapping("/searchUserFilter")
    public ResponseEntity<?> searchUser(
            @RequestParam(name = "searchValue", required = false) String searchValue,
            @RequestParam(name = "address", required = false) String address,
            @RequestParam(name = "userId", required = false) Integer userId) {

        List<User> userList = userService.searchUserFilter(searchValue, address, userId);
        return ResponseEntity.ok(userList);
    }
}
