package com.example.securityl.service;

import com.example.securityl.model.Product;
import com.example.securityl.request.ProductRequest.RequestObject;
import com.example.securityl.request.ProductRequest.SearchProduct;
import com.example.securityl.response.ProductResponse.ListProductResponse;
import com.example.securityl.response.ProductResponse.ProductResponse;
import com.example.securityl.response.ProductResponse.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService  {
    ResponseEntity<ResponseObject> createProduct(String productName, String title, String description, double discount, String color, String size, double price, String material, String thumbnail, Integer quantity, String brand, boolean favorite, String categoryName);

    ResponseEntity<ResponseObject> deleteProduct(Integer productId);

    ResponseEntity<ListProductResponse> getAll();

    String uploadImage(MultipartFile file) throws IOException;

    void uploadProductImage(Integer productId, List<String> imageUrls);

    ResponseEntity<ListProductResponse> searchProduct(SearchProduct searchProduct);

    ResponseEntity<ResponseObject> updateProduct(Integer productId, RequestObject requestObject);

    ProductResponse getProductById(Integer productId);
    
    List<Product> searchProducts(String materials, String brand, Double price, String color);

    List<Product> searchProductsVer2(String materials, String brand, Double minPrice, Double maxPrice, String color);

    ResponseEntity<ListProductResponse> viewWishList(boolean favorite);

}
