package com.example.securityl.repositories;

import com.example.securityl.models.TagsBlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsBlogRepository extends JpaRepository<TagsBlog, Long> {
}
