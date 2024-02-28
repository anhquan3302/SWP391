package com.example.securityl.response.UserResponse;

import com.example.securityl.model.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Integer userId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Role role;
    private boolean status;

}
