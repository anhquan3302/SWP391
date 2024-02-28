package com.example.securityl.serviceimpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.securityl.model.Category;
import com.example.securityl.model.CategoryProduct;
import com.example.securityl.model.Enum.Role;
import com.example.securityl.model.ImageProduct;
import com.example.securityl.model.Products;
import com.example.securityl.repository.*;
import com.example.securityl.request.ProductRequest.RequestObject;
import com.example.securityl.request.ProductRequest.SearchProduct;
import com.example.securityl.response.ObjectResponse.ResponseObject;
import com.example.securityl.service.JwtService;
import com.example.securityl.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CategoryProductRepository categoryProductRepository;
    private final Cloudinary cloudinary;
    private final CategoryRepository categoryRepository;

    public ResponseEntity<ResponseObject> createProduct(String productName, String title, String description, double discount, String color, String size, double price, String material, String thumbnail, Integer quantity, String brand,boolean favorite, Integer categoryId) {
        try {
            Date date = new Date();
            String token = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                    .getRequest()
                    .getHeader("Authorization")
                    .substring(7);
            String userEmail = jwtService.extractUsername(token);
            var requester = userRepository.findUserByEmail(userEmail).orElse(null);
            if (requester == null || !(requester.getRole().equals(Role.STAFF) || requester.getRole().equals(Role.ADMIN))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject("Fail", "Unauthorized", null));
            }
            if (size == null || size.trim().isEmpty() || productName == null || productName.trim().isEmpty() || description == null || description.trim().isEmpty() || title == null || title.trim().isEmpty() || price <= 0) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Invalid request body", null));
            }
            if(quantity <= 0 || quantity > 10){
                return ResponseEntity.status(400).body(new ResponseObject("Fail","Quantity does not exist",null));
            }
            if (!(requester.getRole().equals(Role.ADMIN) || requester.getRole().equals(Role.STAFF))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseObject("Fail", "Forbidden", null));
            }
            Category category = categoryRepository.findById(categoryId).orElse(null);
            if (category == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Fail", "Category not found", null));
            }
            List<Category> categoryList = Collections.singletonList(category);
            Products product = Products.builder()
                    .thumbnail(thumbnail)
                    .productName(productName.trim())
                    .description(description.trim())
                    .title(title.trim())
                    .color(color)
                    .discount(discount)
                    .createdAt(date)
                    .updatedAt(date)
                    .materials(material)
                    .size(size)
                    .price(price)
                    .brand(brand)
                    .quantity(quantity)
                    .favorite(favorite)
                    .categories(categoryList)
                    .user(userRepository.findUserIdByEmail(userEmail))
                    .build();
            Products savedProduct = productRepository.save(product);
            CategoryProduct categoryProduct = CategoryProduct.builder()
                    .product(product)
                    .category(category)
                    .build();
            categoryProductRepository.save(categoryProduct);
            return ResponseEntity.ok(new ResponseObject("Success", "Product created successfully", savedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseObject("Fail", "Internal server error", null));
        }
    }





    @Override
    public ResponseEntity<ResponseObject> updateProduct(
            Integer productId, RequestObject requestObject) {
        try {
            String token = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                    .getRequest()
                    .getHeader("Authorization")
                    .substring(7);
            String userEmail = jwtService.extractUsername(token);
            var requester = userRepository.findUserByEmail(userEmail).orElse(null);
            if (requester == null) {
                return ResponseEntity.status(404).body(new ResponseObject("Fail", "User not found", null));
            }
            if (requestObject.getSize() == null || requestObject.getSize().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Size must be greater than 0", null));
            }
            if (requestObject.getDescription() == null || requestObject.getDescription().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Description is empty", null));
            }
            if (requestObject.getTitle() == null || requestObject.getTitle().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Title is empty", null));
            }
            if (requestObject.getPrice() <= 0) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Price must be greater than 0", null));
            }
            if (!(requester.getRole().equals(Role.ADMIN) || requester.getRole().equals(Role.STAFF))) {
                return ResponseEntity.status(403).body(new ResponseObject("Fail", "You don't have permission", null));
            }
            if(requestObject.getBrand()  == null || requestObject.getBrand().trim().isEmpty() ){
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Brand is empty", null));

            }
            if (requestObject.getQuantity() <= 0 || requestObject.getQuantity() > 10) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Quantity must be greater than 0 and < 10", null));
            }
            var checkProduct = productRepository.findById(productId).orElse(null);
            if (checkProduct != null) {
                checkProduct.setThumbnail(requestObject.getThumbnail());
                checkProduct.setSize(requestObject.getSize());
                checkProduct.setDescription(requestObject.getDescription().trim());
                checkProduct.setTitle(requestObject.getTitle().trim());
                checkProduct.setPrice(requestObject.getPrice());
                checkProduct.setUpdatedAt(new Date());
                checkProduct.setQuantity(requestObject.getQuantity());
                checkProduct.setBrand(requestObject.getBrand());
                var updateProduct = productRepository.save(checkProduct);
                return ResponseEntity.ok(new ResponseObject("Success", "Update product success", updateProduct));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("Fail", "Internal Server Error", null));
        }
        return ResponseEntity.status(500).body(ResponseObject.builder()
                .status("Fail")
                .message("Internal sever error ")
                .payload(null)
                .build());
    }

    @Override
    public Products getProductById(Integer productId) {
        try {
            return productRepository.findProductByProductId(productId).orElse(null);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public ResponseEntity<ResponseObject> getProductByCategory(String categoryName) {
        List<Products> productsList = productRepository.findAllByCategoryName(categoryName);
        if (productsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Fail", "No products found for category: " + categoryName, null));
        } else {
            return ResponseEntity.ok(new ResponseObject("Success", "Products found for category: " + categoryName, productsList));
        }
    }


    @Override
    public ResponseEntity<ResponseObject> deleteProduct(Integer productId) {
        var checkProduct = productRepository.findProductByProductId(productId).orElse(null);
        if (checkProduct != null) {
            productRepository.delete(checkProduct);
            return ResponseEntity.ok(new ResponseObject("Success", "Delete successful", checkProduct));
        } else {
            return ResponseEntity.status(404).body(new ResponseObject("Fail", "Delete fail", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getAll() {
        try {
            var listProduct = productRepository.findAll();
            return ResponseEntity.ok(new ResponseObject("Success", "List all product", listProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Not find list product", null));
        }
    }




    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return (String) uploadResult.get("url");
    }


    @Override
    public void uploadProductImage(Integer productId, List<String> imageUrls) {
        try {
            Products product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
            List<ImageProduct> imageProducts = new ArrayList<>();
            for (String imageUrl : imageUrls) {
                ImageProduct imageProduct = new ImageProduct();
                imageProduct.setImageUrl(imageUrl);
                imageProduct.setProduct(product);
                imageProducts.add(imageProduct);
            }
            product.setImageProducts(imageProducts);
            productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload product images: " + e.getMessage(), e);
        }
    }

    @Override
    public ResponseEntity<ResponseObject> searchProduct(SearchProduct searchProduct) {
        try {
            List<Products> productsList = productRepository.findAll();
            List<Products> showProduct = new ArrayList<>();
            if (searchProduct.getProductId() != null) {
                showProduct = productsList.stream()
                        .filter(n -> n.getProductId() == searchProduct.getProductId())
                        .toList();
            }
            if (searchProduct.getProductName() != null && !searchProduct.getProductName().trim().isEmpty()) {
                showProduct = productsList.stream()
                        .filter(n -> n.getProductName().toLowerCase().contains(searchProduct.getProductName().toLowerCase()))
                        .toList();
            }
            return ResponseEntity.ok(new ResponseObject("Success", "List users", showProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Not found product", null));
        }
    }

    @Override
    public List<Products> searchProducts(String materials, String brand, Double price, String color) {
        if (materials == null && brand == null && price == null && color == null) {
            // Trả về toàn bộ danh sách sản phẩm nếu không có bộ lọc được áp dụng
            return productRepository.findAll();
        } else {
            // Thực hiện tìm kiếm và áp dụng các bộ lọc
            return productRepository.findProductsByFilter(materials, brand, price, color);
        }
    }

    public List<Products> searchProductsVer2(String materials, String brand, Double minPrice, Double maxPrice, String color) {

            return productRepository.findProductsByFilter2(materials, brand, minPrice, maxPrice, color);


    }


}

