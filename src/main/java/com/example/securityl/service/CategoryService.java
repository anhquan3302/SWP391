package com.example.securityl.service;

import com.example.securityl.model.Category;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    Category createCategory(Category category);
}
