package com.example.securityl.service;

import com.example.securityl.request.UserRequest.SearchRequest;
import com.example.securityl.model.User;
import com.example.securityl.request.UserRequest.UserRequest;
import com.example.securityl.request.UserRequest.UpdateUserRequest;
import com.example.securityl.response.UserResponse.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    CreateResponse createUser(UserRequest request);
    ResponseEntity<UpdateUserResponse> updateUser(Integer userId, UpdateUserRequest updateUserRequest);
    ResponseEntity<DeleteResponse> deleteUser(Integer userId);
    UserResponse getUser(Integer id);
    ResponseEntity<ResponseUser> findAllUser();

    ResponseEntity<ResponseUser> searchUsers(SearchRequest req);

    User findByEmailForMail(String email);

    User saveUserForMail(User user);

    List<User> searchUserFilter(String searchValue, String address, Integer userId);
}
