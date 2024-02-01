package com.example.securityl.response.UserResponse;

import com.example.securityl.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserResponse {
    private String status;
    private String message;
    private User updateUser;
}
