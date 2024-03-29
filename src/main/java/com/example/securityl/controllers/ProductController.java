package com.example.securityl.controllers;


import com.example.securityl.Responses.ProductCreateResponse;
import com.example.securityl.Responses.ProductListResponse;
import com.example.securityl.Responses.ProductResponse;
import com.example.securityl.components.LocalizationUtils;
import com.example.securityl.dtos.ProductDto;
import com.example.securityl.dtos.Top5ProductDto;
import com.example.securityl.exceptions.DataNotFoundException;
import com.example.securityl.models.Product;
import com.example.securityl.services.impl.ProductService;
import com.example.securityl.utills.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final LocalizationUtils localizationUtils;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody ProductDto productDTO,
            BindingResult result
    ) throws DataNotFoundException {
        ProductCreateResponse productCreateResponse = new ProductCreateResponse();
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            productCreateResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_BRAND_FAILED));
            productCreateResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(productCreateResponse);
        }
        Product tagsProduct = productService.createProduct(productDTO);
        productCreateResponse.setStatus("success");
        productCreateResponse.setProduct(tagsProduct);
        return ResponseEntity.ok(productCreateResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDto productDTO,
            BindingResult result
    ) throws Exception {
        ProductCreateResponse productCreateResponse = new ProductCreateResponse();
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            productCreateResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_BRAND_FAILED));
            productCreateResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(productCreateResponse);
        }
        Product tagsProduct = productService.updateProduct(id, productDTO);
        productCreateResponse.setProduct(tagsProduct);
        return ResponseEntity.ok(productCreateResponse);
    }
//    @CrossOrigin
    @GetMapping("")
    public ResponseEntity<ProductListResponse> getAllProduct(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "brandIds", required = false) String brandIds,
            @RequestParam(value = "tagsProductIds", required = false) String tagsProductIds,
            @RequestParam(value = "categoryIds", required = false) String categoryIds
    ) {
        List<Long> parsedBrandIds = parseIds(brandIds);
        List<Long> parsedTagsProductIds = parseIds(tagsProductIds);
        List<Long> parsedCategoryIds = parseIds(categoryIds);
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("id").descending()
        );
        Page<ProductResponse> productPage = productService.getAllProducts(
                keyword, pageRequest, minPrice, maxPrice, parsedBrandIds, parsedTagsProductIds, parsedCategoryIds);
        int totalPages = productPage.getTotalPages();
        Long totalProduct = productPage.getTotalElements();
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse.builder()
                .products(products)
                .totalPages(totalPages)
                .totalProducts(totalProduct)
                .build());
    }


//    @CrossOrigin
    @GetMapping("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) throws Exception {
        Product product = productService.getProductById(id);
        return  ResponseEntity.ok(product);
    }

//    @CrossOrigin
    @GetMapping("/get_all")
    public ResponseEntity<List<ProductResponse>> getAll(){
        List<ProductResponse> productResponses = productService.getAll();
        return ResponseEntity.ok(productResponses);
    }
//    @CrossOrigin
    @GetMapping("/category")
    public ResponseEntity<?> getCategoryProduct(
            @Valid @RequestParam(value = "category_id", required = false) Long categoryId) {
        try {
            List<ProductResponse> products;
            if (categoryId == null) {
                // Nếu không có categoryId được chỉ định, trả về tất cả các sản phẩm
                products = productService.getAll(); // Cần phải thêm phương thức getAllProducts() trong ProductService
            } else {
                products = productService.getProductByCategory(categoryId);
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/top5-best-selling")
    public List<Top5ProductDto> getTop5BestSellingProducts() {
        return productService.getTop5BestSellingProducts();
    }

    private List<Long> parseIds(String ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        return Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

//    @GetMapping("/top-5-products-best-rating")
//    public List<Product> getTop5Products() {
//        return productService.getTop5Products();
//    }




//    @GetMapping("/get-wish-list")
//    public ResponseEntity<ProductListFavorite> getWishList() {
//        return productService.findTopFavoriteProducts();
//    }



//    @GetMapping("/top5-favorites")
//    public ResponseEntity<List<ProductDto>> getTop5FavoriteProducts() {
//        List<ProductDto> top5FavoriteProducts = productService.findTop5FavoriteProducts();
//        return ResponseEntity.ok().body(top5FavoriteProducts);
//    }




}
