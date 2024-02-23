package com.example.securityl.service;

import com.example.securityl.request.UserRequest.SearchRequest;
import com.example.securityl.model.User;
import com.example.securityl.request.UserRequest.CreateUserRequest;
import com.example.securityl.request.UserRequest.UpdateUserRequest;
import com.example.securityl.response.UserResponse.CreateResponse;
import com.example.securityl.response.UserResponse.DeleteResponse;
import com.example.securityl.response.UserResponse.ResponseUser;
import com.example.securityl.response.UserResponse.UpdateUserResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    CreateResponse createUser(CreateUserRequest request);
    ResponseEntity<UpdateUserResponse> updateUser(String email, UpdateUserRequest updateUserRequest);
    ResponseEntity<DeleteResponse> deleteUser(String email);
    User getUser(Integer id);
    ResponseEntity<ResponseUser> findAllUser();

    ResponseEntity<ResponseUser> searchUsers(SearchRequest req);

    User findByEmailForMail(String email);

    User saveUserForMail(User user);
}
