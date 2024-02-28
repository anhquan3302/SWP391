package com.example.securityl.response.UserResponse;

import com.example.securityl.model.Enum.Role;
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
    private String status;
    private String messages;
    private String email;
    private String name;
    private Integer userId;
    private Role role;

    @JsonProperty("token")
    private String token;

    @JsonProperty("refesh_token")
    private String refeshToken;
}
