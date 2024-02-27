package com.example.securityl.service;

import com.example.securityl.model.Blog;
import com.example.securityl.request.BlogRequest.BlogRequest;


import com.example.securityl.response.BlogResponse.ResponseObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BlogService {
//    ResponseObject createBlog(String title, String content);


    List<Blog> searchBlog(String createdAt, String searchValue, String orderBy);

    ResponseEntity<ResponseObject> updateBlog(int blogId, BlogRequest blogRequest);

    ResponseEntity<ResponseObject> deleteBlog(int blogId);

    ResponseEntity<ResponseObject> findBlogById(int blogId);

    ResponseEntity<ResponseObject> findAllBlog();


//    Page<Blog> findPaginated(int page, int size);

    void uploadBlogImage(Integer blogId, List<String> imageUrls);
    String uploadBImage(MultipartFile file) throws IOException;

    ResponseEntity<ResponseObject> createBlog(BlogRequest blogRequest);
}
