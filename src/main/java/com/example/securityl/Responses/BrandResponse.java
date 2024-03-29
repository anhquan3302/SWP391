package com.example.securityl.Responses;


import com.example.securityl.models.Brand;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    private List<String> errors;

    private Brand brand;
}
