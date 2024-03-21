package com.example.securityl.dto.request.response.UserResponse;

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


