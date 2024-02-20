package com.example.securityl.request.UserRequest;

import lombok.Data;

@Data
public class SearchRequest {
    private Integer userId;
    private String name;
    private String email;
}
