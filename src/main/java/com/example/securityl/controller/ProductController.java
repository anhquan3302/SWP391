package com.example.securityl.controller;

import com.example.securityl.model.Product;
import com.example.securityl.request.ProductRequest.RequestObject;
import com.example.securityl.response.ProductResponse.ProductResponse;
import com.example.securityl.response.ProductResponse.ResponseObject;
import com.example.securityl.service.FireBaseService;
import com.example.securityl.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
@PreAuthorize("hasAnyRole('admin')")
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
public class ProductController {
    private final ProductService productService;
    private final FireBaseService fireBaseService;

    @PostMapping("/createProduct")
    public ResponseEntity<ResponseObject> createProduct(
                                                        @RequestParam("productName") String productName,
                                                        @RequestParam("title") String title,
                                                        @RequestParam("description") String description,
                                                        @RequestParam("discount") double discount,
                                                        @RequestParam("color") String color,
                                                        @RequestParam("size") String size,
                                                        @RequestParam("price") double price,
                                                        @RequestParam("material") String material,
                                                        @RequestParam("thumbnail") String thumbnail,
                                                        @RequestParam("quantity") Integer quantity,
                                                        @RequestParam("brand") String brand,
                                                        @RequestParam("favorite") boolean favorite,
                                                        @RequestParam("categoryName")String categoryName) {
        return productService.createProduct(productName,title, description, discount, color, size, price, material,thumbnail,quantity,brand,favorite,categoryName);
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

    @PostMapping("/upload_firebase/{productId}")
    public ResponseEntity<?> uploadProductImages(@PathVariable Integer productId,
                                                 @RequestParam("files") MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return ResponseEntity.badRequest().body("No files uploaded");
        }

        List<String> imageUrls = fireBaseService.uploadImages(files);

        productService.uploadProductImage(productId, imageUrls);

        return ResponseEntity.ok("Images uploaded successfully");
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


    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(
            @RequestParam(name = "materials", required = false) String materials,
            @RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "price", required = false) Double price,
            @RequestParam(name = "color", required = false) String color
    ) {
        List<ProductResponse> productList = productService.searchProducts(materials, brand, price, color);
        return ResponseEntity.ok(productList);
    }


    @GetMapping("/searchProductsVer2")
    public ResponseEntity<?> searchProductsVer2(
            @RequestParam(name = "materials", required = false) String materials,
            @RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice,
            @RequestParam(name = "color", required = false) String color
    ) {
        List<Product> productList = productService.searchProductsVer2(materials, brand, minPrice, maxPrice, color);
        return ResponseEntity.ok(productList);
    }

}
