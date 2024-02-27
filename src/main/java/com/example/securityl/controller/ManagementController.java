package com.example.securityl.controller;

import com.example.securityl.model.Blog;
import com.example.securityl.model.Products;
import com.example.securityl.request.BlogRequest.BlogRequest;
import com.example.securityl.request.ProductRequest.RequestObject;
import com.example.securityl.request.ProductRequest.SearchProduct;
import com.example.securityl.response.ObjectResponse.ResponseObject;
import com.example.securityl.service.BlogService;
import com.example.securityl.service.CategoryProductService;
import com.example.securityl.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/management")
@PreAuthorize("hasAnyRole('USER','ADMIN','STAFF')")
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
public class ManagementController {
    private final ProductService productService;
    private final CategoryProductService categoryProductService;

    private final BlogService blogService;

    @GetMapping("/getAllProduct")
    public ResponseEntity<ResponseObject> getAllProduct(){
        return productService.getAll();
    }

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
                                                        @RequestParam("categoryId")Integer categoryId) {
        return productService.createProduct(productName,title, description, discount, color, size, price, material,thumbnail,quantity,brand,categoryId);
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

    @GetMapping("/getListProductByCategory")
    private ResponseEntity<ResponseObject> getProductByCategoryName(@RequestParam String name) {
        return productService.getProductByCategory(name);
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

    @PostMapping("/createBlog")
    public ResponseEntity<ResponseObject> createBlog(@RequestBody BlogRequest blogRequest) {
        return blogService.createBlog(blogRequest);
    }

    @PostMapping("/getProduct")
    private ResponseEntity<ResponseObject> searchProducts(@RequestBody SearchProduct searchProduct){
        return productService.searchProduct(searchProduct);
    }

    @GetMapping("/getProductById/{productId}")
    private Products getProductById(@PathVariable Integer productId){
        return productService.getProductById(productId);
    }
    @GetMapping("/searchBlog")
    public ResponseEntity<?> searchBlog(@RequestParam(name = "createdAt",required = false)
                                            String createdAt,
                                            @RequestParam(name = "searchValue",required = false)
                                            String searchValue,
                                            @RequestParam(name = "orderBy",required = false)
                                            String orderBy)
    {
        List<Blog> syllabusList = blogService.searchBlog(createdAt, searchValue, orderBy);
        return ResponseEntity.ok(syllabusList);
    }



    @PutMapping("/updateBlog/{blogId}")
    public ResponseEntity<ResponseObject> updateBlog(
            @PathVariable int blogId,
            @RequestBody BlogRequest blogRequest) {
        return blogService.updateBlog(blogId, blogRequest);
    }

    @DeleteMapping("/deleteBlog/{blogId}")
    public ResponseEntity<ResponseObject> deleteBlog(@PathVariable int blogId) {
        return blogService.deleteBlog(blogId);
    }


    @GetMapping("/findBlog/{blogId}")
    public ResponseEntity<ResponseObject> findBlogById(@PathVariable int blogId) {
        return blogService.findBlogById(blogId);
    }

    @GetMapping("/getAllBlog")
    private ResponseEntity<ResponseObject> getAllBlog(){
        return blogService.findAllBlog();
    }


}

