package com.example.securityl.services;



import com.example.securityl.models.CategoryBlog;

import java.util.List;

public interface ICategoriesBlogService {

    List<CategoryBlog> getAllCategoriesBlog();
    CategoryBlog getCategoryById(Long id);
    CategoryBlog saveCategory(CategoryBlog categoryBlog);
    void deleteCategory(Long id);

}
