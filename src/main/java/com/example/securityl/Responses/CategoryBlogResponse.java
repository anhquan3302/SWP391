package com.example.securityl.Responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryBlogResponse {

    private Long id;
    private String name;
    private String code;
}
