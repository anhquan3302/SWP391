package com.example.securityl.service;

import com.example.securityl.model.Category;
import com.example.securityl.response.ProductResponse.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    Category createCategory(Category category);

    ResponseEntity<ResponseObject> findAllCategory();
}
