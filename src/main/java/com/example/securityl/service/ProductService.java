package com.example.securityl.service;

import com.example.securityl.model.ImageProduct;
import com.example.securityl.model.Products;
import com.example.securityl.request.ProductRequest.RequestObject;
import com.example.securityl.request.ProductRequest.SearchProduct;
import com.example.securityl.response.ProductResponse.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService  {
    ResponseEntity<ResponseObject> createProduct(String productName,String title, String description, double discount, String color, String size, double price, String material,String thumbnail,Integer categoryId);

    ResponseEntity<ResponseObject> deleteProduct(Integer productId);

    ResponseEntity<ResponseObject> getAll();

    String uploadImage(MultipartFile file) throws IOException;

    void uploadProductImage(Integer productId, List<String> imageUrls);

    ResponseEntity<ResponseObject> searchProduct(SearchProduct searchProduct);

    ResponseEntity<ResponseObject> updateProduct(Integer productId, RequestObject requestObject);

    Products getProductById(Integer productId);
}
