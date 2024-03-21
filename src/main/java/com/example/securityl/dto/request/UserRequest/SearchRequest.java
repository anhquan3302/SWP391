
package com.example.securityl.dto.request.UserRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchRequest {
    private Integer userId;
    private String name;
    private String email;
}