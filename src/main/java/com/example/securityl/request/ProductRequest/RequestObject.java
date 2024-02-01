package com.example.securityl.request.ProductRequest;

import com.example.securityl.entity.Category;
import com.example.securityl.entity.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestObject {
    private String title;
    private String thumbnail;
    private Integer discount;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private String material;
    private double size;
    private String color;
    private double price;

}
