package com.example.securityl.controller;

import com.example.securityl.model.Category;
import com.example.securityl.model.CategoryProduct;
import com.example.securityl.request.CategoryRequest.RequestCategory;
import com.example.securityl.request.ProductRequest.RequestObject;
import com.example.securityl.response.ProductResponse.ResponseObject;
import com.example.securityl.service.CategoryProductService;
import com.example.securityl.service.CategoryService;
import com.example.securityl.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/management")
public class ManagementController {
    private final ProductService productService;
    private final CategoryProductService categoryProductService;
    private final CategoryService categoryService;

    @GetMapping("/getAllProduct")
    public ResponseEntity<ResponseObject> getAllProduct(){
        return productService.getAll();
    }

    @PostMapping("/createProduct")
    public ResponseEntity<ResponseObject> createProduct(
                                                        @RequestParam("title") String title,
                                                        @RequestParam("description") String description,
                                                        @RequestParam("discount") Integer discount,
                                                        @RequestParam("color") String color,
                                                        @RequestParam("size") double size,
                                                        @RequestParam("price") double price,
                                                        @RequestParam("material") String material) {
        return productService.createProduct(title, description, discount, color, size, price, material);
    }

    @PostMapping("/upload-images/{productId}")
    public ResponseEntity<?> uploadImages(@PathVariable Integer productId,
                                          @RequestParam("files") MultipartFile[] files) {
        try {
            if (files == null || files.length == 0) {
                return ResponseEntity.badRequest().body("No files uploaded");
            }
            List<String> imageUrls = new ArrayList<>();
            for (MultipartFile file : files) {
                String imageUrl = productService.uploadImage(file);
                imageUrls.add(imageUrl);
            }
            productService.uploadProductImage(productId, imageUrls);

            return ResponseEntity.ok().body("Images uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload images: " + e.getMessage());
        }
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<ResponseObject> updateProduct(
            @PathVariable Integer productId,
            @RequestBody RequestObject requestObject) {
        return productService.updateProduct(productId, requestObject);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    private ResponseEntity<ResponseObject> deleteProduct(
            @PathVariable Integer productId){
        return productService.deleteProduct(productId);
    }

    @PostMapping("/createCategory/{productId}")
    public ResponseEntity<ResponseObject> addProductToCategory(@PathVariable Integer productId,
                                                               @RequestBody RequestCategory requestCategory) {
        try {
            return categoryProductService.addProductToCategory(productId, requestCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseObject.builder()
                            .status("Fail")
                            .message("Unauthorized access")
                            .build());
        }
    }


}

