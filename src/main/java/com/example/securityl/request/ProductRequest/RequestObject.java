package com.example.securityl.request.ProductRequest;

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
public class RequestObject {
    private String title;
    private double discount;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private String material;
    private String size;
    private String color;
    private double price;
    private String productName;
    private String thumbnail;
    private int quantity;
    private String brand;
    private boolean favorite;
}
