package com.example.securityl.request.CategoryRequest;

import com.example.securityl.model.Products;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCategory {
    private String categoryName;
    private String categoryDescription;
    private String productName;

}
