package com.example.securityl.serviceimpl;

import com.example.securityl.model.Category;
import com.example.securityl.repository.CategoryRepository;
import com.example.securityl.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    @Override
    public Category createCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category with name " + category.getName() + " already exists");
        }
        Category category1 = Category.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
        return categoryRepository.save(category1);
    }
}
