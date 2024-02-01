package com.example.securityl.serviceimpl;

import com.example.securityl.entity.Enum.Role;
import com.example.securityl.entity.Products;
import com.example.securityl.repository.ProductRepository;
import com.example.securityl.repository.UserRepository;
import com.example.securityl.request.ProductRequest.RequestObject;
import com.example.securityl.response.ProductResponse.ResponseObject;
import com.example.securityl.service.JwtService;
import com.example.securityl.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ResponseObject> createProduct(RequestObject requestObject) {
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
            if(!(requester.getRole().equals(Role.ADMIN) || requester.getRole().equals(Role.STAFF))){
                return ResponseEntity.status(403).body(new ResponseObject("Fail","You don't permission",null));
            }
            Products product = Products.builder()
                    .description(requestObject.getDescription().trim())
                    .title(requestObject.getTitle().trim())
                    .color(requestObject.getColor())
                    .thumbnail(requestObject.getThumbnail())
                    .discount(requestObject.getDiscount())
                    .createdAt(date)
                    .updatedAt(date)
                    .materials(requestObject.getMaterial())
                    .size(requestObject.getSize())
                    .price(requestObject.getPrice())
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
    public ResponseEntity<ResponseObject> updateProduct(Integer productId,RequestObject requestObject) {
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
            if(requestObject.getSize() != 0){
                return ResponseEntity.badRequest().body(new ResponseObject("Fail","Size is greater than 0",null));
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


            var checkProduct = productRepository.findById(productId).orElse(null);
            if (checkProduct != null) {
                checkProduct.setThumbnail(requestObject.getThumbnail());
                checkProduct.setDescription(requestObject.getDescription());
                checkProduct.setUpdatedAt(requestObject.getUpdatedAt());
                checkProduct.setColor(requestObject.getColor());
                checkProduct.setCreatedAt(requestObject.getCreatedAt());
                checkProduct.setTitle(requestObject.getTitle());
                checkProduct.setDiscount(requestObject.getDiscount());
                checkProduct.setMaterials(requestObject.getMaterial());
                var updateProduct = productRepository.save(checkProduct);
                return ResponseEntity.ok(new ResponseObject("Success", "Update product success", updateProduct));
            }
        }catch (Exception e){
                return ResponseEntity.badRequest().body(new ResponseObject("Fail","Update product fail",null));
        }
        return ResponseEntity.status(500).body(ResponseObject.builder()
                        .status("Fail")
                        .message("Internal sever error ")
                        .payload(null)
                .build());
    }
}
