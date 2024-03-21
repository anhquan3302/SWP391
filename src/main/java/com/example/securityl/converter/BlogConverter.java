package com.example.securityl.converter;

import com.example.securityl.model.Blog;



public class BlogConverter {
    public static Blog toDto(Blog entity) {
        Blog dto = new Blog();
        dto.setBlogId(entity.getBlogId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setThumbnail(entity.getThumbnail());
        dto.setUser(entity.getUser());

        return dto;
    }

    public static Blog toEntity(Blog dto) {
        Blog entity = new Blog();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setThumbnail(dto.getThumbnail());
        return entity;
    }


    public static Blog toEntity(Blog dto, Blog entity) {
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setThumbnail(dto.getThumbnail());

        return entity;
    }


}
