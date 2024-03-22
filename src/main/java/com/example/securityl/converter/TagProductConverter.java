package com.example.securityl.converter;


import com.example.securityl.dtos.TagProductDto;
import com.example.securityl.models.TagsProduct;
import org.springframework.stereotype.Component;

@Component
public class TagProductConverter {
    public static TagProductDto toDto(TagsProduct entity) {
        TagProductDto dto = new TagProductDto();
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        return dto;
    }

    public static TagsProduct toEntity(TagProductDto dto) {
        TagsProduct entity = new TagsProduct();
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        return entity;
    }

    public static TagsProduct toEntity(TagProductDto dto, TagsProduct entity) {
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        return entity;
    }
}
