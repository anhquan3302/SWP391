package com.example.securityl.service;

import com.example.securityl.model.ImageProduct;
import com.example.securityl.request.ProductRequest.RequestObject;
import com.example.securityl.response.ProductResponse.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService  {
    ResponseEntity<ResponseObject> createProduct(String title, String description, Integer discount, String color, double size, double price, String material);



    ResponseEntity<ResponseObject> deleteProduct(Integer productId);

    ResponseEntity<ResponseObject> getAll();

    void updateProductImage(Integer productId, String imageUrl);

    String uploadImage(MultipartFile file) throws IOException;

    void uploadProductImage(Integer productId, List<String> imageUrls);

    ResponseEntity<ResponseObject> updateProduct(Integer productId, RequestObject requestObject);
}
