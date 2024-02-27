package com.example.securityl.controller;

import com.example.securityl.model.Blog;
import com.example.securityl.request.BlogRequest.BlogRequest;
import com.example.securityl.response.ProductResponse.ResponseObject;
import com.example.securityl.service.BlogService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/BlogController")
public class BlogController {


    private final BlogService blogService;

    @PostMapping("/createBlog")
    public ResponseEntity<ResponseObject> createBlog(@RequestBody BlogRequest blogRequest) {
        return blogService.createBlog(blogRequest);
    }

    @GetMapping("/searchBlog")
    public ResponseEntity<?> searchBlog(@RequestParam(name = "createdAt", required = false)
                                        String createdAt,
                                        @RequestParam(name = "searchValue", required = false)
                                        String searchValue,
                                        @RequestParam(name = "orderBy", required = false)
                                        String orderBy) {
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

    // Endpoint để tìm kiếm blog theo id
    @GetMapping("/findBlog/{blogId}")
    public ResponseEntity<ResponseObject> findBlogById(@PathVariable int blogId) {
        return blogService.findBlogById(blogId);
    }

    @GetMapping("/getAllBlog")
    private ResponseEntity<ResponseObject> getAllBlog() {
        return blogService.findAllBlog();
    }
}
