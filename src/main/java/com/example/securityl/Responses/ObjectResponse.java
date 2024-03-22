package com.example.securityl.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObjectResponse {
    private String status;
    private  String message;
    private UserResponse userResponse;
}
