package com.example.securityl.converter;

import com.example.securityl.dto.request.response.ProductResponse.ProductResponse;
import com.example.securityl.model.Product;

public class ProductConverter {
    public static Product toEntity(Product dto) {
        Product entity = new Product();
        //entity.setId(dto.getId());
        entity.setProductName(dto.getProductName());
        entity.setDescription(dto.getDescription());
        entity.setThumbnail(dto.getThumbnail());
        entity.setPrice(dto.getPrice());
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());
        entity.setMaterials(dto.getMaterials());
        //entity.setRating(dto.getRating());
        entity.setSize(dto.getSize());
        entity.setColor(dto.getColor());
        entity.setQuantity(dto.getQuantity());
        entity.setFavorite(dto.isFavorite());
        entity.setDiscount(dto.getDiscount());
//        entity.setCreatedAt(dto.getCreatedAt());
//        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }

    public static ProductResponse toResponse(Product product){
        ProductResponse productResponse = ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .materials(product.getMaterials())
                .size(product.getSize())
                .color(product.getColor())
                .quantity(product.getQuantity())
                .discount(product.getDiscount())
                .imageProducts(product.getImageProducts())
                .build();
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }
}
