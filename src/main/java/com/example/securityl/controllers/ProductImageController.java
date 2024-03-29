package com.example.securityl.controllers;


import com.example.securityl.dtos.ProductImageDto;
import com.example.securityl.models.ProductImages;
import com.example.securityl.services.impl.ProductImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("${api.prefix}/product_image")
@RequiredArgsConstructor
public class ProductImageController {
    private final ProductImageService productImageService;

    @PostMapping("/upload/{id}")
    public ResponseEntity<List<ProductImageDto>> uploadProductImages(
            @PathVariable("id") Long productId,
            @RequestParam("files") List<MultipartFile> files) {
        try {
            List<ProductImageDto> uploadedImages = productImageService.uploadFiles(files, productId);
            return ResponseEntity.ok(uploadedImages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList()); // or handle the error response as needed
        }
    }
//    @CrossOrigin
    @GetMapping("/product/{id}")
    public ResponseEntity<List<ProductImages>> getImageByProducts(
            @Valid @PathVariable Long id
    ){
        List<ProductImages> productImages = productImageService.getImagesByProduct(id);
        return ResponseEntity.ok(productImages);
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<List<ProductImageDto>> updateProductImages(
//            //@RequestParam("productId") Long productId,
//            @PathVariable("id") Long productId,
//            @RequestParam("files") List<MultipartFile> files) {
//        try {
//            List<ProductImageDto> updatedImages = productImageService.updateFiles(files, productId);
//            return ResponseEntity.ok(updatedImages);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Collections.emptyList()); // hoặc xử lý phản hồi lỗi theo nhu cầu
//        }
//    }
}
