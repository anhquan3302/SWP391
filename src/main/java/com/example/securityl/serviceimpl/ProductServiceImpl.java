package com.example.securityl.serviceimpl;

import com.example.securityl.model.Enum.Role;
import com.example.securityl.model.Products;
import com.example.securityl.repository.ProductRepository;
import com.example.securityl.repository.UserRepository;
import com.example.securityl.response.ProductResponse.ResponseObject;
import com.example.securityl.service.JwtService;
import com.example.securityl.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;




    @Override
    public ResponseEntity<ResponseObject> createProduct(MultipartFile file, String title, String description, Integer discount, String color, double size, double price, String material) {
        try {
            Date date = new Date();
            String token = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                    .getRequest()
                    .getHeader("Authorization")
                    .substring(7);
            String userEmail = jwtService.extractUsername(token);
            var requester = userRepository.findUserByEmail(userEmail).orElse(null);
            if (requester == null) {
                return ResponseEntity.status(404).body(new ResponseObject("Fail", "User not found", null));
            }
            if (!(requester.getRole().equals(Role.STAFF) || requester.getRole().equals(Role.ADMIN))) {
                return ResponseEntity.status(403).body(new ResponseObject("Fail", "You don't have permission", null));
            }
            if (size <= 0) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Size must be greater than 0", null));
            }
            if (description == null || description.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Description is empty", null));
            }
            if (title == null || title.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Title is empty", null));
            }
            if (price <= 0) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Price must be greater than 0", null));
            }
            if(!(requester.getRole().equals(Role.ADMIN) || requester.getRole().equals(Role.STAFF))){
                return ResponseEntity.status(403).body(new ResponseObject("Fail","You don't permission",null));
            }

            Products product = Products.builder()
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

    @Override
    public ResponseEntity<ResponseObject> updateProduct(
            Integer productId,
            MultipartFile file,
            String title,
            String description,
            Integer discount,
            String color,
            double size,
            double price,
            String material) {
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
            if (size <= 0) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Size must be greater than 0", null));
            }
            if (description == null || description.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Description is empty", null));
            }
            if (title == null || title.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Title is empty", null));
            }
            if (price <= 0) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Price must be greater than 0", null));
            }
            if (!(requester.getRole().equals(Role.ADMIN) || requester.getRole().equals(Role.STAFF))) {
                return ResponseEntity.status(403).body(new ResponseObject("Fail", "You don't have permission", null));
            }
            var checkProduct = productRepository.findById(productId).orElse(null);
            if (checkProduct != null) {
                checkProduct.setSize(size);
                checkProduct.setDescription(description.trim());
                checkProduct.setTitle(title.trim());
                checkProduct.setPrice(price);
//                if (file != null && !file.isEmpty()) {
//                    byte[] imageData = file.getBytes();
//                    String thumbnailBase64 = Base64.getEncoder().encodeToString(imageData);
//                    checkProduct.setThumbnail(thumbnailBase64.getBytes());
//                }
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
        if(checkProduct != null) {
            productRepository.delete(checkProduct);
            return ResponseEntity.ok(new ResponseObject("Success","Delete successful",checkProduct));
        }else{
            return ResponseEntity.status(404).body(new ResponseObject("Fail","Delete fail", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getAll() {
        try{
            var listProduct = productRepository.findAll();
                return ResponseEntity.ok(new ResponseObject("Success","List all product",listProduct));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseObject("Fail","Not find list product",null));
        }
    }
}
