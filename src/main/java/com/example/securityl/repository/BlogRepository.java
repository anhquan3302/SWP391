package com.example.securityl.repository;

import com.example.securityl.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {



    boolean existsByTitle(String title);

    @Query("SELECT b FROM Blog b WHERE " +
            "(:createdAt IS NULL OR DATE(b.createdAt) = :createdAt) AND " +
            "(:searchValue IS NULL OR LOWER(b.title) LIKE %:searchValue% OR LOWER(b.content) LIKE %:searchValue%) " +
            "ORDER BY CASE WHEN :orderBy = 'createdAt' THEN b.createdAt END ASC")
    List<Blog> findBlogsByFilter(String createdAt, String searchValue, String orderBy);
}
