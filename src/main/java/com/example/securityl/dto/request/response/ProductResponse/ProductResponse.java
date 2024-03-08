package com.example.securityl.dto.request.response.ProductResponse;

import com.example.securityl.model.Category;
import com.example.securityl.model.ImageProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Integer productId;
    private String title;
    private double discount;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private String size;
    private String productName;
    private String thumbnail;
    private boolean favorite;
    private Integer quantity;
    private String color;
    private double price;
    private String materials;
    private String brand;
    private List<ImageProduct> imageProducts;

}
