package com.example.securityl.dto.request.response.UserResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LoginGoogleResponse {
    private String status;
    private String messages;
    @JsonProperty("token")
    private String token;
}
