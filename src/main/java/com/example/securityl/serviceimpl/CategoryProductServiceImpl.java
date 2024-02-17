package com.example.securityl.serviceimpl;

import com.example.securityl.model.Category;
import com.example.securityl.model.CategoryProduct;
import com.example.securityl.model.Products;
import com.example.securityl.repository.CategoryProductRepository;
import com.example.securityl.repository.CategoryRepository;
import com.example.securityl.repository.ProductRepository;
import com.example.securityl.request.CategoryRequest.RequestCategory;
import com.example.securityl.response.ProductResponse.ResponseObject;
import com.example.securityl.service.CategoryProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryProductServiceImpl implements CategoryProductService {
    private final CategoryProductRepository categoryProductRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public ResponseEntity<ResponseObject> addProductToCategory(Integer productId, RequestCategory requestCategory) {
        Products product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .status("Fail")
                    .message("Product not found")
                    .build());
        }
        Category category = Category.builder()
                .name(requestCategory.getCategoryName())
                .description(requestCategory.getCategoryDescription())
                .build();
        categoryRepository.save(category);
        CategoryProduct categoryProduct = CategoryProduct.builder()
                .category(category)
                .product(product)
                .build();
        categoryProductRepository.save(categoryProduct);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .status("Success")
                .message("Category product created successfully")
                .payload(categoryProduct)
                .build());
    }

}

