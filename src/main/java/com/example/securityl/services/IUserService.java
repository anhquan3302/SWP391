
package com.example.securityl.services;


import com.example.securityl.Responses.AuthenticationResponse;
import com.example.securityl.Responses.ObjectResponse;
import com.example.securityl.Responses.UpdateUserResponse.UpdateUserResponse;
import com.example.securityl.Responses.UserResponse;
import com.example.securityl.dtos.AdditionalInfoDto;
import com.example.securityl.dtos.AuthenticationDTO;
import com.example.securityl.dtos.UserDto;
import com.example.securityl.dtos.analysis.UserStatsDTO;
import com.example.securityl.exceptions.DataNotFoundException;
import com.example.securityl.models.Enum.Role;
import com.example.securityl.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface IUserService {
    ResponseEntity<ObjectResponse> createUser(UserDto request);

    AuthenticationResponse authenticate(AuthenticationDTO request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    User findByEmailForMail(String email);

    User saveUserForMail(User user);

    List<User> findAllUser();

    UserResponse getUserById(Long id);

    ResponseEntity<ObjectResponse> deleteUser(Long userId);

    ResponseEntity<UpdateUserResponse> updateUser(Long userId, UserDto updateUserRequest);

    UserStatsDTO getUserStats();

    void receiveAndConfirmConsultation(Long id, AdditionalInfoDto additionalInfoDto) throws DataNotFoundException;

    void cancelBooking(Long bookingId) throws DataNotFoundException;
    Page<UserResponse> getAllUsers(PageRequest pageRequest, Role role);

}



