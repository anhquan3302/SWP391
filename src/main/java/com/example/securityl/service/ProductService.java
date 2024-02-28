package com.example.securityl.service;

import com.example.securityl.model.Product;
import com.example.securityl.request.ProductRequest.RequestObject;
import com.example.securityl.request.ProductRequest.SearchProduct;
import com.example.securityl.response.ObjectResponse.ResponseObject;
import com.example.securityl.response.ProductResponse.CreateProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService  {
    ResponseEntity<CreateProductResponse> createProduct(String productName, String title, String description, double discount, String color, String size, double price, String material, String thumbnail, Integer quantity, String brand, boolean favorite, Integer categoryId);

    ResponseEntity<ResponseObject> deleteProduct(Integer productId);

    ResponseEntity<ResponseObject> getAll();

    String uploadImage(MultipartFile file) throws IOException;

    void uploadProductImage(Integer productId, List<String> imageUrls);

    ResponseEntity<ResponseObject> searchProduct(SearchProduct searchProduct);

    ResponseEntity<ResponseObject> updateProduct(Integer productId, RequestObject requestObject);

    Product getProductById(Integer productId);

//    ResponseEntity<ResponseObject> getProductByCategory(String categoryName);

    List<Product> searchProducts(String materials, String brand, Double price, String color);

    List<Product> searchProductsVer2(String materials, String brand, Double minPrice, Double maxPrice, String color);
}
