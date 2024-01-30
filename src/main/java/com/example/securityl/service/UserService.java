package com.example.securityl.service;

import com.example.securityl.entity.User;
import com.example.securityl.request.CreateUserRequest;
import com.example.securityl.request.UpdateUserRequest;
import com.example.securityl.response.CreateResponse;
import com.example.securityl.response.DeleteResponse;
import com.example.securityl.response.ResponseUser;
import com.example.securityl.response.UpdateUserResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    CreateResponse createUser(CreateUserRequest request);
    ResponseEntity<UpdateUserResponse> updateUser(String email, UpdateUserRequest updateUserRequest);
    ResponseEntity<DeleteResponse> deleteUser(String email);
    User getUser(Integer id);
    ResponseEntity<ResponseUser> findAllUser();
}
