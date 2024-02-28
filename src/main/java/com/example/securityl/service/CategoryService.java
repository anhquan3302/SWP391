package com.example.securityl.service;

import com.example.securityl.model.Category;
import com.example.securityl.request.CategoryRequest.RequestCategory;
import com.example.securityl.response.ObjectResponse.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Category createCategory(RequestCategory requestCategory);

    ResponseEntity<ResponseObject> findAllCategory();

    List<Category> findCategoryById(Integer categoryId);

    ResponseEntity<ResponseObject> updateCategory(Integer categoryId, RequestCategory requestCategory);

    ResponseEntity<ResponseObject> deleteCategory(Integer categoryId);
}
