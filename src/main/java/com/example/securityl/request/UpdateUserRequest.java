package com.example.securityl.request;

import com.example.securityl.entity.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String name;
    private String address;
    private String phone;
    private String email;
    private String password;
    private Role role;
    private boolean status;
}
