package com.example.securityl.controller;

import com.example.securityl.request.ProductRequest.RequestObject;
import com.example.securityl.response.ProductResponse.ResponseObject;
import com.example.securityl.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/management")
public class ManagementController {
    private final ProductService productService;

    @GetMapping
    public String get() {
        return "Get: managemnt controller";
    }

    @PostMapping("/createProduct")
    public ResponseEntity<ResponseObject> createProduct(
            @RequestBody RequestObject requestObject) {
        return productService.createProduct(requestObject);
    }


    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<ResponseObject> updateProduct(
            @PathVariable Integer productId,
            @RequestBody RequestObject requestObject) {
        return productService.updateProduct(productId,requestObject);
    }
}

