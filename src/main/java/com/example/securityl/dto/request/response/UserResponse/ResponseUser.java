package com.example.securityl.dto.request.response.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUser {
    private String status;
    private String message;
    private List<UserResponse> userList;
}
