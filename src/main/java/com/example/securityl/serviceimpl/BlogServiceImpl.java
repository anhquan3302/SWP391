package com.example.securityl.serviceimpl;

import com.example.securityl.model.Blog;
import com.example.securityl.repository.BlogRepository;
import com.example.securityl.request.BlogRequest.BlogRequest;
import com.example.securityl.response.ProductResponse.ResponseObject;
import com.example.securityl.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service

public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public ResponseEntity<ResponseObject> createBlog(BlogRequest blogRequest) {
        try {
            Blog blog = new Blog();
            blog.setTitle(blogRequest.getTitle());
            blog.setContent(blogRequest.getContent());
            blog.setCreatedAt(new Date());
            blog.setUpdatedAt(new Date());
            // Set user if necessary

            Blog savedBlog = blogRepository.save(blog);
            return ResponseEntity.ok(new ResponseObject("Success", "Create blog success", savedBlog));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("Fail", "Internal Server Error", null));
        }
    }
}
