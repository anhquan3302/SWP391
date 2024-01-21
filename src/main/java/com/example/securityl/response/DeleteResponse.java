package com.example.securityl.response;

import com.example.securityl.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteResponse {
    private String status;
    private String message;
    private User deleteUser;
}
