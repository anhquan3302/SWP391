package com.example.securityl.converter;


import com.example.securityl.dtos.CategoryDto;
import com.example.securityl.models.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    public static CategoryDto toDto(Category entity){
        CategoryDto dto = new CategoryDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        return dto;
    }

    public static Category toEntity(CategoryDto dto){
        Category entity = new Category();
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        return entity;
    }

    public static Category toEntity(CategoryDto dto, Category entity){
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        return entity;
    }
}
