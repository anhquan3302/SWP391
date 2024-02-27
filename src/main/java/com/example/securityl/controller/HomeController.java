package com.example.securityl.controller;

import com.example.securityl.model.Products;
import com.example.securityl.request.ProductRequest.SearchProduct;
import com.example.securityl.response.ObjectResponse.ResponseObject;
import com.example.securityl.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;
    @GetMapping("/getAllProduct")
    public ResponseEntity<ResponseObject> getAllProduct(){
        return productService.getAll();
    }

    @GetMapping("/getListProductByCategory")
    private ResponseEntity<ResponseObject> getProductByCategoryName(@RequestParam String name) {
        return productService.getProductByCategory(name);
    }

    @GetMapping("/getProductById/{productId}")
    private Products getProductById(@PathVariable Integer productId){
        return productService.getProductById(productId);
    }

    @PostMapping("/getProduct")
    private ResponseEntity<ResponseObject> searchProducts(@RequestBody SearchProduct searchProduct){
        return productService.searchProduct(searchProduct);
    }
}
