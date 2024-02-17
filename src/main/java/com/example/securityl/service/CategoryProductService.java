package com.example.securityl.service;


import com.example.securityl.model.CategoryProduct;
import com.example.securityl.request.CategoryRequest.RequestCategory;
import com.example.securityl.response.ProductResponse.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CategoryProductService {
    ResponseEntity<ResponseObject> addProductToCategory(Integer productId, RequestCategory requestCategory);
}
