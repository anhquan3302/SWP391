//package com.example.securityl.services.impl;
//
//
//import com.example.securityl.converter.CategoryConverter;
//import com.example.securityl.dtos.CategoryDto;
//import com.example.securityl.models.Category;
//import com.example.securityl.repositories.CategoryRepository;
//import com.example.securityl.services.ICategoryService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class CategoryService implements ICategoryService {
//
//    private final CategoryRepository categoryRepository;
//    @Override
//    public CategoryDto createCategory(CategoryDto categoryDto) {
//        String generatedCode = generateCodeFromName(categoryDto.getName());
//        Category category = CategoryConverter.toEntity(categoryDto);
//        category.setCode(generatedCode);
//        category = categoryRepository.save(category);
//        return CategoryConverter.toDto(category);
//    }
//
//    @Override
//    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
//        String generatedCode = generateCodeFromName(categoryDto.getName());
//        Category category = new Category();
//        Optional<Category> existingCategory = categoryRepository.findById(id);
//        if(existingCategory.isPresent()){
//            Category oldCategory = existingCategory.get();
//            category = CategoryConverter.toEntity(categoryDto, oldCategory);
//            category.setCode(generatedCode);
//        }
//        category = categoryRepository.save(category);
//        return CategoryConverter.toDto(category);
//    }
//
//    @Override
//    public List<CategoryDto> getAllCategories() {
//        List<CategoryDto> result = new ArrayList<>();
//        List<Category> categories = categoryRepository.findAll();
//        for (Category item :categories
//             ) {
//            CategoryDto dto =CategoryConverter.toDto(item);
//            result.add(dto);
//        }
//        return result;
//    }
//
//
//    private String generateCodeFromName(String name) {
//        // Thực hiện logic tạo mã của bạn ở đây
//        // Đơn giản, bạn có thể sử dụng một logic cơ bản như loại bỏ khoảng trắng và chuyển đổi thành chữ in hoa
//        return name.replaceAll("\\s", "-").toLowerCase();
//    }
//}
