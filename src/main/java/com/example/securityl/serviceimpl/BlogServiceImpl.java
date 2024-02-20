package com.example.securityl.serviceimpl;

import com.example.securityl.model.Blog;
import com.example.securityl.repository.BlogRepository;

import com.example.securityl.request.BlogRequest.BlogRequest;
import com.example.securityl.response.ProductResponse.ResponseObject;
import com.example.securityl.service.BlogService;
import lombok.RequiredArgsConstructor;
import com.example.securityl.request.BlogRequest.BlogRequest;
import com.example.securityl.response.ProductResponse.ResponseObject;
import com.example.securityl.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.google.common.base.Strings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<Blog> searchBlog(String createdAt, String searchValue, String orderBy) {
        List<Blog> blogList = blogRepository.findAll();
        if(!Strings.isNullOrEmpty(createdAt)){
            blogList = blogList.stream().filter(n -> {
                return new SimpleDateFormat("yyyy-MM-dd").format(n.getCreatedAt()).equals(createdAt);
            }).collect(Collectors.toList());
        }
//        if(!Strings.isNullOrEmpty(searchValue)){
//            blogList = blogList.stream().filter(n -> n.getBlogId().trim().toLowerCase().contains(searchValue.trim().toLowerCase())
//                    || n.getTopicCode().trim().toLowerCase().contains(searchValue.trim().toLowerCase())).collect(Collectors.toList());
//        }
        return blogList;
    }
    @Override
    public ResponseEntity<ResponseObject> updateBlog(int blogId, BlogRequest blogRequest) {
        try {
            Blog existingBlog = blogRepository.findById(blogId).orElse(null);
            if (existingBlog == null) {
                // Xử lý trường hợp không tìm thấy blog
            }
            existingBlog.setTitle(blogRequest.getTitle());
            existingBlog.setContent(blogRequest.getContent());
            existingBlog.setUpdatedAt(new Date());

            Blog updatedBlog = blogRepository.save(existingBlog);
            return ResponseEntity.ok(new ResponseObject("Success", "Update blog success", updatedBlog));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> deleteBlog(int blogId) {
        try {
            Blog blog = blogRepository.findById(blogId).orElse(null);
            if (blog == null) {
                // Xử lý trường hợp không tìm thấy blog
            }
            blogRepository.delete(blog);
            return ResponseEntity.ok(new ResponseObject("Success", "Delete blog success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> findBlogById(int blogId) {
        try {
            Blog blog = blogRepository.findById(blogId).orElse(null);
            if (blog == null) {
                // Xử lý trường hợp không tìm thấy blog
            }
            return ResponseEntity.ok(new ResponseObject("Success", "Find blog success", blog));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("Fail", "Internal Server Error", null));
        }
    }
}
