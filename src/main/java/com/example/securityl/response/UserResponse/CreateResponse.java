package com.example.securityl.response.UserResponse;

import com.example.securityl.model.Enum.Role;
import com.example.securityl.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateResponse {
    private String status;
    private String message;
    private UserResponse userResponse;
}


