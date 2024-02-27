package com.example.securityl.serviceimpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.securityl.model.*;
import com.example.securityl.repository.BlogRepository;

import com.example.securityl.repository.UserRepository;
import com.example.securityl.request.BlogRequest.BlogRequest;
import com.example.securityl.response.ProductResponse.ResponseObject;
import com.example.securityl.service.BlogService;
import com.example.securityl.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.example.securityl.request.BlogRequest.BlogRequest;
import com.example.securityl.response.ProductResponse.ResponseObject;
import com.example.securityl.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.google.common.base.Strings;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service

public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final Cloudinary cloudinary;

    private final UserService userService;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository, UserRepository userRepository, Cloudinary cloudinary, UserService userService) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.cloudinary = cloudinary;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<ResponseObject> createBlog(BlogRequest blogRequest) {
        try {

            if (blogRequest.getTitle() == null || blogRequest.getTitle().isEmpty() ||
                    blogRequest.getContent() == null || blogRequest.getContent().isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Title and content are required", null));
            }
            if (blogRepository.existsByTitle(blogRequest.getTitle())) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Title already exists", null));
            }
            if (!isValidTitle(blogRequest.getTitle()) || !isValidContent(blogRequest.getContent())) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Title and content must be at least 5 characters long and title must not exceed 50 characters, content must not exceed 1000 characters", null));
            }


            Blog blog = new Blog();
            blog.setTitle(blogRequest.getTitle());
            blog.setContent(blogRequest.getContent());
            blog.setCreatedAt(new Date());
            blog.setUpdatedAt(new Date());
            var user = userRepository.findUsersByUserId(blogRequest.getUserId());//huy code do


            Blog savedBlog = blogRepository.save(blog);


            return ResponseEntity.ok(new ResponseObject("Success", "Create blog success", savedBlog));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("Fail", "Internal Server Error", null));
        }
    }

    private boolean isValidTitle(String title) {
        return title != null && title.length() >= 5 && title.length() <= 50;
    }

    private boolean isValidContent(String content) {
        return content != null && content.length() >= 5 && content.length() <= 1000;
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
        if (!Strings.isNullOrEmpty(searchValue)) {
            String searchRegex = "(?i).*" + searchValue + ".*"; // Ignore case
            blogList = blogList.stream()
                    .filter(n -> n.getTitle().matches(searchRegex) || n.getContent().matches(searchRegex))
                    .collect(Collectors.toList());
        }

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

    @Override
    public ResponseEntity<ResponseObject> findAllBlog() {
        var blogAll = blogRepository.findAll();
        return ResponseEntity.ok().body(new ResponseObject("Success","List blog",blogAll));
    }

//    @Override
//    public Page<Blog> findPaginated(int page, int size) {
//        Pageable pageable = PageRequest.of(pageNo, pageSize);
//        return this.blogRepository.findAll(pageable);
//    }

    @Override
    public void uploadBlogImage(Integer blogId, List<String> imageUrls) {
        try {
            Blog blog = blogRepository.findById(blogId)
                    .orElseThrow(() -> new EntityNotFoundException("Blog not found with id: " + blogId));

            List<ImageBlog> imageBlogs = new ArrayList<>();
            for (String imageUrl : imageUrls) {
                ImageBlog imageBlog = new ImageBlog();
                imageBlog.setImageUrl(imageUrl);
                imageBlog.setBlog(blog);
                imageBlogs.add(imageBlog);
            }

            blog.setImageBlogs(imageBlogs);
            blogRepository.save(blog);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload product images: " + e.getMessage(), e);
        }
    }

    @Override
    public String uploadBImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return (String) uploadResult.get("url");
    }


}
