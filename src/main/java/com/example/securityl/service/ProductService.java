package com.example.securityl.service;

import com.example.securityl.request.ProductRequest.RequestObject;
import com.example.securityl.response.ProductResponse.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService  {
    ResponseEntity<ResponseObject> createProduct(MultipartFile file, String title, String description, Integer discount, String color, double size, double price, String material);

    ResponseEntity<ResponseObject> updateProduct(Integer productId ,MultipartFile file, String title, String description, Integer discount, String color, double size, double price, String material);

    ResponseEntity<ResponseObject> deleteProduct(Integer productId);

    ResponseEntity<ResponseObject> getAll();
}
