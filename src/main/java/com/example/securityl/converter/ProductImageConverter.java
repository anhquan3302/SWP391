package com.example.securityl.converter;

import com.example.securityl.model.ImageProduct;

public class ProductImageConverter {
    public static ImageProduct toDto(ImageProduct entity) {
        ImageProduct dto = new ImageProduct();
        dto.setProduct(entity.getProduct());
        dto.setImageUrl(entity.getImageUrl());
        return dto;
    }

    public static ImageProduct toEntity(ImageProduct dto) {
        ImageProduct entity = new ImageProduct();
        entity.setImageUrl(dto.getImageUrl());
        return entity;
    }

    public static ImageProduct toEntity(ImageProduct dto, ImageProduct entity) {
        entity.setImageUrl(dto.getImageUrl());
        return entity;
    }
}
