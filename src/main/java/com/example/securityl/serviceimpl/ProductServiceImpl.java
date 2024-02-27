package com.example.securityl.serviceimpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.securityl.model.Enum.Role;
import com.example.securityl.model.ImageProduct;
import com.example.securityl.model.Products;
import com.example.securityl.repository.ImageProductRepository;
import com.example.securityl.repository.ProductRepository;
import com.example.securityl.repository.UserRepository;
import com.example.securityl.request.ProductRequest.RequestObject;
import com.example.securityl.request.ProductRequest.SearchProduct;
import com.example.securityl.request.ProductRequest.SearchProductRequest;
import com.example.securityl.response.ProductResponse.ResponseObject;
import com.example.securityl.response.UserResponse.ResponseUser;
import com.example.securityl.service.JwtService;
import com.example.securityl.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ImageProductRepository imageProductRepository;
    private final Cloudinary cloudinary;


    @Override
    public ResponseEntity<ResponseObject> createProduct(String productName,String title, String description, Integer discount, String color, double size, double price, String material) {
        try {
            Products product = createNewProduct(productName,title, description, discount, color, size, price, material);
            if (product == null) {
                return ResponseEntity.status(500).body(new ResponseObject("Fail", "Failed to create product", null));
            }
            Products savedProduct = productRepository.save(product);
            return ResponseEntity.ok(ResponseObject.builder()
                    .status("Success")
                    .message("Create Product Success")
                    .payload(savedProduct)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("Fail", "Internal Server Error", null));
        }
    }



    private Products createNewProduct(String productName,String title, String description, Integer discount, String color, double size, double price, String material) {
        try {
            Date date = new Date();
            String token = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                    .getRequest()
                    .getHeader("Authorization")
                    .substring(7);
            String userEmail = jwtService.extractUsername(token);
            var requester = userRepository.findUserByEmail(userEmail).orElse(null);
            if (requester == null || !(requester.getRole().equals(Role.STAFF) || requester.getRole().equals(Role.ADMIN))) {
                return null;
            }
            if (size <= 0 || productName ==null|| productName.trim().isEmpty() || description == null || description.trim().isEmpty() || title == null || title.trim().isEmpty() || price <= 0) {
                return null;
            }
            if (!(requester.getRole().equals(Role.ADMIN) || requester.getRole().equals(Role.STAFF))) {
                return null;
            }

            return Products.builder()
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
                    .user(userRepository.findUserIdByEmail(userEmail))
                    .build();
        } catch (Exception e) {
            return null;
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
            if (requestObject.getSize() <= 0) {
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
            var checkProduct = productRepository.findById(productId).orElse(null);
            if (checkProduct != null) {
                checkProduct.setSize(requestObject.getSize());
                checkProduct.setDescription(requestObject.getDescription().trim());
                checkProduct.setTitle(requestObject.getTitle().trim());
                checkProduct.setPrice(requestObject.getPrice());
                checkProduct.setUpdatedAt(new Date());
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
    public ResponseEntity<ResponseObject> deleteProduct(Integer productId) {
        var checkProduct = productRepository.findProductsByProductId(productId).orElse(null);
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
    public void updateProductImage(Integer productId, String imageUrl) {
        try {
            if (productId == null) {
                throw new IllegalArgumentException("Product ID cannot be null");
            }
            if (imageUrl == null || imageUrl.isEmpty()) {
                throw new IllegalArgumentException("Image URL cannot be null or empty");
            }
            Products product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));
            productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product image: " + e.getMessage(), e);
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




}

