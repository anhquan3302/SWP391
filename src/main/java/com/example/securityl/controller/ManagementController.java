package com.example.securityl.controller;

import com.example.securityl.model.*;
import com.example.securityl.request.BlogRequest.BlogRequest;
import com.example.securityl.request.BlogRequest.BlogRequest;
import com.example.securityl.request.CategoryRequest.RequestCategory;
import com.example.securityl.request.ProductRequest.RequestObject;
import com.example.securityl.request.ProductRequest.SearchProduct;
import com.example.securityl.response.ProductResponse.ResponseObject;
import com.example.securityl.service.BlogService;
import com.example.securityl.service.BlogService;
import com.example.securityl.service.CategoryProductService;
import com.example.securityl.service.CategoryService;
import com.example.securityl.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/management")
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
public class ManagementController {
    private final ProductService productService;
    private final CategoryProductService categoryProductService;
    private final CategoryService categoryService;
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
                                                        @RequestParam("size") double size,
                                                        @RequestParam("price") double price,
                                                        @RequestParam("material") String material) {
        return productService.createProduct(productName,title, description, discount, color, size, price, material);
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

    @PostMapping("/createBlog")
    public ResponseEntity<ResponseObject> createBlog(@RequestBody BlogRequest blogRequest) {
        return blogService.createBlog(blogRequest);
    }
    @PostMapping("/upload-images/{blogId}")
    public ResponseEntity<?> uploadImagesBlog(@PathVariable Integer blogId,
                                          @RequestParam("files") MultipartFile[] files) {
        try {
            if (files == null || files.length == 0) {
                return ResponseEntity.badRequest().body("No files uploaded");
            }
            List<String> imageUrls = new ArrayList<>();
            for (MultipartFile file : files) {
                String imageUrl = blogService.uploadBImage(file);
                imageUrls.add(imageUrl);
            }
            blogService.uploadBlogImage(blogId, imageUrls);

            return ResponseEntity.ok().body("Images uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload images: " + e.getMessage());
        }
    }
    @GetMapping("/getAllCategory")
    private ResponseEntity<ResponseObject> getAllCategory(){
        return categoryService.findAllCategory();
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

//    @GetMapping
//    public List<Blog> getAllItems(@RequestParam(defaultValue = "0") int page,
//                                  @RequestParam(defaultValue = "10") int size) {
//        Page<Blog> pageItems = blogService.findPaginated(page, size);
//        return pageItems.getContent();
//    }
}

