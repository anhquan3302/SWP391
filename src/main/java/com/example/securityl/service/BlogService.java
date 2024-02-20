package com.example.securityl.service;

import com.example.securityl.model.Blog;
import com.example.securityl.request.BlogRequest.BlogRequest;
import com.example.securityl.response.ProductResponse.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BlogService {
//    ResponseObject createBlog(String title, String content);

    ResponseEntity<com.example.securityl.response.ProductResponse.ResponseObject> createBlog(BlogRequest blogRequest);

    List<Blog> searchBlog(String createdAt, String searchValue, String orderBy);

    ResponseEntity<ResponseObject> updateBlog(int blogId, BlogRequest blogRequest);

    ResponseEntity<ResponseObject> deleteBlog(int blogId);

    ResponseEntity<ResponseObject> findBlogById(int blogId);
}
