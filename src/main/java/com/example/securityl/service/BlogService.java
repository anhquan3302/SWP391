package com.example.securityl.service;

import com.example.securityl.request.BlogRequest.BlogRequest;
import com.example.securityl.response.BlogResponse.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface BlogService {
//    ResponseObject createBlog(String title, String content);

    ResponseEntity<com.example.securityl.response.ProductResponse.ResponseObject> createBlog(BlogRequest blogRequest);
}
