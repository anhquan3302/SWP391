package com.example.securityl.service;

import com.example.securityl.model.Blog;
import com.example.securityl.dto.request.BlogRequest.BlogRequest;


import com.example.securityl.dto.request.response.BlogResponse.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BlogService {
//    ResponseObject createBlog(String title, String content);


    

    ResponseEntity<ResponseObject> updateBlog(int blogId, BlogRequest blogRequest);

    ResponseEntity<ResponseObject> deleteBlog(int blogId);

    ResponseEntity<ResponseObject> findBlogById(int blogId);

    ResponseEntity<ResponseObject> findAllBlog();


//    Page<Blog> findPaginated(int page, int size);

    void uploadBlogImage(Integer blogId, List<String> imageUrls);
    String uploadBImage(MultipartFile file) throws IOException;

    ResponseEntity<ResponseObject> createBlog(BlogRequest blogRequest);

    List<Blog> searchBlog(String createdAt, String searchValue, String orderBy);
}
