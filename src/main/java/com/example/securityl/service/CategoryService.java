package com.example.securityl.service;

import com.example.securityl.model.Category;
import com.example.securityl.request.CategoryRequest.RequestCategory;
import com.example.securityl.response.ProductResponse.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    Category createCategory(RequestCategory requestCategory);

    ResponseEntity<ResponseObject> findAllCategory();
}
