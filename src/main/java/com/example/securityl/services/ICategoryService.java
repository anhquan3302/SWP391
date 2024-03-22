package com.example.securityl.services;



import com.example.securityl.dtos.CategoryDto;

import java.util.List;

public interface ICategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(Long id, CategoryDto categoryDto);

    List<CategoryDto> getAllCategories();
}
