package com.example.securityl.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    private String staus;
    private String messages;
    private UserResponse user;
    @JsonProperty("token")
    private String token;
    private String role;
    @JsonProperty("refesh_token")
    private String refeshToken;
}
