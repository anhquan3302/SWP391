package com.example.securityl.converter;


import com.example.securityl.Responses.BlogResponse;
import com.example.securityl.dtos.BlogDto;
import com.example.securityl.models.Blog;
import com.example.securityl.models.CategoryBlog;
import com.example.securityl.models.TagsBlog;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BlogConverter {


    public static BlogDto toDto(Blog entity) {
        BlogDto dto = new BlogDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setThumbnail(entity.getThumbnail());
        dto.setImageUrls(entity.getImageUrls());
        dto.setUserBlogId(entity.getUser().getId());
//        dto.setTagBlogId(entity.getTagsBlog().getId());
//        dto.setCategoryBlogId(entity.getCategoryBlog().getId());
        return dto;
    }

    public static Blog toEntity(BlogDto dto) {
        Blog entity = new Blog();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setThumbnail(dto.getThumbnail());
        return entity;
    }


    public static Blog toEntity(BlogDto dto, Blog entity) {
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setThumbnail(dto.getThumbnail());

        return entity;
    }

    public static BlogResponse toResponse(Blog blog) {
        List<Long> categoryIds = blog.getCategories().stream()
                .map(CategoryBlog::getId)
                .collect(Collectors.toList());

        List<Long> tagBlogIds = blog.getTagsBlog().stream()
                .map(TagsBlog::getId)
                .collect(Collectors.toList());

        List<String> categoryNames = blog.getCategories().stream()
                .map(CategoryBlog::getName)
                .collect(Collectors.toList());

        List<String> tagsBlogName = blog.getTagsBlog().stream()
                .map(TagsBlog::getTagName)
                .collect(Collectors.toList());



        BlogResponse blogResponse = BlogResponse.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .thumbnail(blog.getThumbnail())
                .imageUrls(blog.getImageUrls())
                .userBlogId(blog.getUser().getId())
                .tagBlogIds(tagBlogIds)
                .categoryBlogIds(categoryIds)
                .categoryNames(categoryNames)
                .tagsBlogName(tagsBlogName)
                .userFullName(blog.getUser().getFullName())
                .active(blog.isActive())
                .createdAt(blog.getCreatedAt())
                .updatedAt(blog.getUpdatedAt())
                .build();
        return blogResponse;
    }

}
