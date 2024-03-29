package com.example.securityl.Responses.UpdateUserResponse;

import com.example.securityl.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserResponse {
    private String status;
    private String message;
    private User update;
}
