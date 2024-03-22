package com.example.securityl.converter;


import com.example.securityl.dtos.ProductImageDto;
import com.example.securityl.models.ProductImages;
import org.springframework.stereotype.Component;

@Component
public class ProductImageConverter {
    public static ProductImageDto toDto(ProductImages entity) {
        ProductImageDto dto = new ProductImageDto();
        dto.setProductId(entity.getProduct().getId());
        dto.setImageUrl(entity.getImageUrl());
        return dto;
    }

    public static ProductImages toEntity(ProductImageDto dto) {
        ProductImages entity = new ProductImages();
        entity.setImageUrl(dto.getImageUrl());
        return entity;
    }

    public static ProductImages toEntity(ProductImageDto dto, ProductImages entity) {
        entity.setImageUrl(dto.getImageUrl());
        return entity;
    }
}
