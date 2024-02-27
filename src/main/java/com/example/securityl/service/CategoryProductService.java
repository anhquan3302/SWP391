package com.example.securityl.service;


import com.example.securityl.request.CategoryRequest.RequestCategory;
import com.example.securityl.response.ObjectResponse.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CategoryProductService {
    ResponseEntity<ResponseObject> addProductToCategory(Integer productId, RequestCategory requestCategory);
}
