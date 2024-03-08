package com.example.securityl.serviceimpl;

import com.example.securityl.model.Category;
import com.example.securityl.repository.CategoryRepository;
import com.example.securityl.dto.request.CategoryRequest.RequestCategory;
import com.example.securityl.dto.request.response.ObjectResponse.ResponseObject;
import com.example.securityl.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public Category createCategory(RequestCategory requestCategory) {
       Category category1 = Category.builder()
                .name(requestCategory.getCategoryName())
                .description(requestCategory.getCategoryDescription())
                .build();
        return categoryRepository.save(category1);
    }

    @Override
    public ResponseEntity<ResponseObject> findAllCategory() {
        var categoryAll = categoryRepository.findAll();
        return ResponseEntity.ok().body(new ResponseObject("Success","List category",categoryAll));
    }

    @Override
    public List<Category> findCategoryById(Integer categoryId) {
        List<Category> category = categoryRepository.findAllByCategoryId(categoryId);
        return  category;

    }

    @Override
    public ResponseEntity<ResponseObject> updateCategory(Integer categoryId, RequestCategory requestCategory) {
        var category = categoryRepository.findById(categoryId).orElse(null);
        if(category == null){
            return ResponseEntity.badRequest().body(new ResponseObject("Fail","Not found Category ",null));
        }
        if(requestCategory.getCategoryName() == null || requestCategory.getCategoryName().trim().isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseObject("Fail","Please fill category name ",null));
        }
        if(requestCategory.getCategoryDescription() == null || requestCategory.getCategoryDescription().trim().isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseObject("Fail","Please fill description ",null));
        }
        category.setName(requestCategory.getCategoryName());
        category.setDescription(requestCategory.getCategoryDescription());
        categoryRepository.save(category);
        return ResponseEntity.ok(new ResponseObject("Success", "Category updated successfully", category));
    }

    @Override
    public ResponseEntity<ResponseObject> deleteCategory(Integer categoryId) {
        return null;
    }
}
