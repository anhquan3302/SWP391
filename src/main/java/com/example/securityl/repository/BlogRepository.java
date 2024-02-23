package com.example.securityl.repository;

import com.example.securityl.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
    Blog findByTitle(String title);


    boolean existsByTitle(String title);
}