package com.example.securityl.serviceimpl;

import com.example.securityl.entity.Enum.Role;
import com.example.securityl.entity.User;
import com.example.securityl.repository.UserRepository;
import com.example.securityl.request.CreateUserRequest;
import com.example.securityl.request.UpdateUserRequest;
import com.example.securityl.response.*;
import com.example.securityl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    Pattern pattern = Pattern.compile(emailRegex);

    @Override
    public CreateResponse createUser(CreateUserRequest request) {
        String token = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest().getHeader("Authorization").substring(7);
        String userEmail = jwtService.extractUsername(token);
        var requester = userRepository.findUserByEmail(userEmail).orElse(null);
        assert requester != null;
        if (!requester.getRole().equals(Role.ADMIN)) {
            return CreateResponse.builder()
                    .status("Create fail")
                    .message("You are not authorized as an admin ")
                    .build();
        }
        if (request.getPhone() == null || request.getPhone().length() != 10) {
            return CreateResponse.builder()
                    .status("Create fail")
                    .message("Phone is not valid. Please provide a valid 10-digit phone number.")
                    .build();
        }
        Matcher matcher = pattern.matcher(request.getEmail());
        if (!matcher.matches()) {
            return CreateResponse
                    .builder()
                    .status("Create fail")
                    .message("Email is not valid")
                    .build();
        }

        var user = User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .status(true)
                .build();
        var existedEmail = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (existedEmail == null) {
            userRepository.save(user);
            return CreateResponse.builder()
                    .status("Success")
                    .message("Create success")
                    .build();
        } else {
            return CreateResponse.builder()
                    .status("Create fail")
                    .message("Account existed")
                    .build();
        }
    }

    @Override
    public ResponseEntity<UpdateUserResponse> updateUser(String email, UpdateUserRequest updateUserRequest) {
        var user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            if (updateUserRequest != null && updateUserRequest.getName() != null && !updateUserRequest.getName().isEmpty()) {
                user.setName(updateUserRequest.getName());
            }
            Matcher matcher = pattern.matcher(updateUserRequest.getEmail());
            if (matcher.matches()) {
                user.setEmail(updateUserRequest.getEmail());
            }
            if ((updateUserRequest.getPhone() != null && updateUserRequest.getPhone().length() == 10)) {
                user.setPhone(user.getPhone());
            }
            if (updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().isEmpty()) {
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
            if (requester.getRole().equals(Role.ADMIN)) {
                user.setRole(updateUserRequest.getRole());
            }
            user.setStatus(updateUserRequest.isStatus());
            return ResponseEntity.ok(UpdateUserResponse.builder()
                    .status("Success")
                    .message("Update User Success")
                    .updateUser(user)
                    .build());
        } else {
            return ResponseEntity.badRequest().body(UpdateUserResponse.builder()
                    .status("Fail")
                    .message("Email not available")
                    .build());
        }
    }

    @Override
    public ResponseEntity<DeleteResponse> deleteUser(String email) {
        var userEmail = userRepository.findByEmail(email).orElse(null);
        if (userEmail != null) {
            userEmail.setStatus(false);
            userRepository.save(userEmail);
            return ResponseEntity.ok(DeleteResponse.builder()
                    .status("Success")
                    .message("Ban success")
                    .deleteUser(userEmail)
                    .build());
        }
        return ResponseEntity.badRequest().body(DeleteResponse.builder()
                .status("Fail")
                .message("Ban fail")
                .deleteUser(userEmail)
                .build());


    }

    @Override
    public User getUser(Integer id) {
        var userId = userRepository.findUsersByUserId(id).orElse(null);
        if (userId != null) {
            return userId;
        } else {
            return null;
        }
    }

    @Override
    public ResponseEntity<ResponseUser> findAllUser() {
        try {
            List<User> userList = userRepository.findAll();
            return ResponseEntity.ok(new ResponseUser("Success","Find list users", userList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseUser.builder()
                            .status("Fail")
                            .message("Find list user fail")
                            .userList(null)
                    .build());
        }


    }
}

