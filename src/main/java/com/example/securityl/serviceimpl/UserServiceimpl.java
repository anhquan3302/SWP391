package com.example.securityl.serviceimpl;

import com.example.securityl.request.UserRequest.SearchRequest;
import com.example.securityl.model.Enum.Role;
import com.example.securityl.model.User;
import com.example.securityl.repository.UserRepository;
import com.example.securityl.request.UserRequest.UserRequest;
import com.example.securityl.request.UserRequest.UpdateUserRequest;
import com.example.securityl.response.UserResponse.*;
import com.example.securityl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceimpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final int MAX_NAME_LENGTH = 15;
    private static final int MAX_PASSWORD_LENGTH = 6;
    Pattern pattern = Pattern.compile(emailRegex);

    @Override
    public CreateResponse createUser(UserRequest request) {
        String token = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest().getHeader("Authorization").substring(7);
        String userEmail = jwtService.extractUsername(token);
        var requester = userRepository.findUserByEmail(userEmail).orElse(null);
        assert requester != null;
        if (!requester.getRole().equals(Role.admin)) {
            return CreateResponse.builder()
                    .status("Create fail")
                    .message("You are not authorized as an admin ")
                    .userResponse(null)
                    .build();
        }
        if (request.getPhone() == null || request.getPhone().length() != 10 ) {
            return CreateResponse.builder()
                    .status("Create fail")
                    .message("Phone is not valid. Please provide a valid 10-digit phone number.")
                    .userResponse(null)
                    .build();
        }
        if (request.getName() == null || request.getName().length() > 15) {
            return CreateResponse.builder()
                    .status("Create fail")
                    .message("Please provide a valid name with maximum length of 15 characters.")
                    .userResponse(null)
                    .build();
        }
        if(request.getPassword() == null || request.getPassword().length() > 5 ){
            return CreateResponse.builder()
                    .status("Create fail")
                    .message("Password with maximum length of 5 characters.")
                    .userResponse(null)
                    .build();
        }
        var checkPhone = userRepository.existsByPhone(request.getPhone());
        if(checkPhone){
            return CreateResponse.builder()
                    .status("Create fail")
                    .message("Phone invalid")
                    .build();
        }
        Matcher matcher = pattern.matcher(request.getEmail());
        if (!matcher.matches()) {
            return CreateResponse
                    .builder()
                    .status("Create fail")
                    .message("Email is not valid")
                    .userResponse(null)
                    .build();
        }
        var user = User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.user)
                .status(true)
                .build();
        var existedEmail = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (existedEmail == null) {
            userRepository.save(user);
            return CreateResponse.builder()
                    .status("Success")
                    .message("Create success")
                    .userResponse(convertToUserResponse(user))
                    .build();
        } else {
            return CreateResponse.builder()
                    .status("Create fail")
                    .message("Account existed")
                    .userResponse(null)
                    .build();
        }
    }



    private UserResponse convertToUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .name(user.getName())
                .address(user.getAddress())
                .role(user.getRole())
                .status(user.isStatus())
                .build();
    }


    @Override
    public ResponseEntity<UpdateUserResponse> updateUser(Integer userId, UpdateUserRequest updateUserRequest) {
        var user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            if (updateUserRequest != null && updateUserRequest.getName() != null && !updateUserRequest.getName().isEmpty() && updateUserRequest.getName().length() <= MAX_NAME_LENGTH) {
                user.setName(updateUserRequest.getName());
            }
            assert updateUserRequest != null;
            Matcher matcher = pattern.matcher(updateUserRequest.getEmail());
            if (matcher.matches()) {
                user.setEmail(updateUserRequest.getEmail());
            }
            if (updateUserRequest.getPhone() != null && updateUserRequest.getPhone().length() == 10) {
                if (userRepository.existsByPhone(updateUserRequest.getPhone())) {
                    return ResponseEntity.badRequest().body(UpdateUserResponse.builder()
                            .status("Fail")
                            .message("Phone already exists")
                            .updateUser(null)
                            .build());
                } else {
                    user.setPhone(updateUserRequest.getPhone());
                }
            }
            if (updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().isEmpty() && updateUserRequest.getPassword().length() <= MAX_PASSWORD_LENGTH) {
                user.setPassword(updateUserRequest.getPassword());
            }
            if (updateUserRequest.getAddress() != null && !updateUserRequest.getAddress().isEmpty()) {
                user.setAddress(updateUserRequest.getAddress());
            }
            String token = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                    .getRequest().getHeader("Authorization").substring(7);
            String userEmail = jwtService.extractUsername(token);
            var requester = userRepository.findUserByEmail(userEmail).orElse(null);
            assert requester != null;
            if (requester.getRole().equals(Role.admin)) {
                user.setRole(updateUserRequest.getRole());
            }
            user.setStatus(true);
            return ResponseEntity.ok(UpdateUserResponse.builder()
                    .status("Success")
                    .message("Update User Success")
                    .updateUser(convertToUserResponse(user))
                    .build());
        } else {
            return ResponseEntity.badRequest().body(UpdateUserResponse.builder()
                    .status("Fail")
                    .message("Email not available")
                    .updateUser(null)
                    .build());
        }
    }

    @Override
    public ResponseEntity<DeleteResponse> deleteUser(Integer userId) {
        var userEmail = userRepository.findById(userId).orElse(null);
        if (userEmail != null) {
            userEmail.setStatus(false);
            userRepository.save(userEmail);
            return ResponseEntity.ok(DeleteResponse.builder()
                    .status("Success")
                    .message("Ban success")
                    .deleteUser(convertToUserResponse(userEmail))
                    .build());
        }
        return ResponseEntity.badRequest().body(DeleteResponse.builder()
                .status("Fail")
                .message("Ban fail")
                .deleteUser(null)
                .build());


    }

    @Override
    public UserResponse getUser(Integer userId) {
        var userOptional = userRepository.findById(userId).orElse(null);
        if (userOptional != null) {
            return convertToUserResponse(userOptional);
        }
        return null;
    }



    @Override
    public ResponseEntity<ResponseUser> findAllUser() {
        try {
            List<User> userList = userRepository.findAll();
            List<UserResponse> userResponseList = convertToUserResponseList(userList);

            return ResponseEntity.ok(new ResponseUser("Success","List users", userResponseList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseUser.builder()
                            .status("Fail")
                            .message("List user fail")
                            .userList(null)
                    .build());
        }


    }

    private List<UserResponse> convertToUserResponseList(List<User> userList) {
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : userList) {
            userResponseList.add(convertToUserResponse(user));
        }
        return userResponseList;
    }


    @Override
    public ResponseEntity<ResponseUser> searchUsers(SearchRequest req) {
        try {
            List<User> userList = userRepository.findAll();
            if(req.getUserId() != null){
                userList = userList.stream().filter(n -> n.getUserId() == req.getUserId()).toList();
            }
            if(req.getName() != null && !req.getName().trim().isEmpty()){
                userList = userList.stream()
                        .filter(n -> n.getName() != null && n.getName().equalsIgnoreCase(req.getName()) || n.getName().contains(req.getName())).toList();

            }
            if(req.getEmail() != null && !req.getEmail().trim().isEmpty()){
                userList = userList.stream().filter(n -> n.getEmail().contains(req.getEmail())).toList();
            }
            return ResponseEntity.ok(new ResponseUser("Success","List users", convertToUserResponseList(userList)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseUser.builder()
                    .status("Fail")
                    .message("List user fail")
                    .userList(null)
                    .build());
        }
    }

    @Override
    public User findByEmailForMail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User saveUserForMail(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> searchUserFilter(String searchValue, String address, Integer userId) {
        return userRepository.findUsersByFilter(searchValue, address, userId);
    }
}

