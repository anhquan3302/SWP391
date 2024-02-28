package com.example.securityl.response.ProductResponse;

import com.example.securityl.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductResponse {
    private String status;
    private String message;
    private Product product;
}
