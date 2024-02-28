package com.example.securityl.response.ProductResponse;

import com.example.securityl.model.Category;
import com.example.securityl.model.Product;
import com.example.securityl.response.CategoryResponse.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductResponse {
    private String status;
    private String message;
    private Object product;
    private CategoryResponse categoryList;
}
