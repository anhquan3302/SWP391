package com.example.securityl.converter;

import com.example.securityl.model.Category;

public class CategoryConverter {
    public static Category toDto(Category entity){
        Category dto = new Category();
        dto.setCategoryId(entity.getCategoryId());
        dto.setName(entity.getName());
        return dto;
    }

    public static Category toEntity(Category dto){
        Category entity = new Category();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public static Category toEntity(Category dto, Category entity){
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
