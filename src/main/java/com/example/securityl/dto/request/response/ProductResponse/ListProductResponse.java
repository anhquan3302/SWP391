package com.example.securityl.dto.request.response.ProductResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListProductResponse {
    private String status;
    private String message;
    private List<ProductResponse> data;
}
