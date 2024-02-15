package com.example.securityl.controller;

import com.example.securityl.request.CategoryRequest.RequestCategory;
import com.example.securityl.response.ProductResponse.ResponseObject;
import com.example.securityl.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/management")
public class ManagementController {
    private final ProductService productService;

    @GetMapping("/getAllProduct")
    public ResponseEntity<ResponseObject> getAllProduct(){
        return productService.getAll();
    }

    @PostMapping("/createProduct")
    public ResponseEntity<ResponseObject> createProduct(
            @RequestParam ("image") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("discount") Integer discount,
            @RequestParam("color") String color,
            @RequestParam("size") double size,
            @RequestParam("price") double price,
            @RequestParam("material") String material) {
        return productService.createProduct(file, title, description, discount, color, size, price, material);
    }




    @PutMapping("/updateProduct")
    public ResponseEntity<ResponseObject> updateProduct(
            @PathVariable Integer productId,
            @RequestParam ("image") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("discount") Integer discount,
            @RequestParam("color") String color,
            @RequestParam("size") double size,
            @RequestParam("price") double price,
            @RequestParam("material") String material) {
        return productService.updateProduct(productId,file, title, description, discount, color, size, price, material);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    private ResponseEntity<ResponseObject> deleteProduct(
            @PathVariable Integer productId){
        return productService.deleteProduct(productId);
    }
}

