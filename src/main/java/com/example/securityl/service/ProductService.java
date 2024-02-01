package com.example.securityl.service;

import com.example.securityl.request.ProductRequest.RequestObject;
import com.example.securityl.response.ProductResponse.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface ProductService  {
    ResponseEntity<ResponseObject> createProduct( RequestObject requestObject);

    ResponseEntity<ResponseObject> updateProduct(Integer productId ,RequestObject requestObject);
}
