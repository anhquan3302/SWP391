package com.example.securityl.dto.request.response.UserResponse;

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
    private UserResponse updateUser;
}
