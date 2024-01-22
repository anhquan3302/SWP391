package com.example.securityl.repository;

import com.example.securityl.entity.Enum.Role;

public interface UserDTO {
    String getName();
    String getEmail();
    String getPhone();
    String getAddress();
    String getPassword();
    Role getRole();
}
