package com.example.securityl.controller;

import com.example.securityl.dto.request.response.Inventory.QuantityResponse;
import com.example.securityl.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory")
public class InventoryController {
    private final ProductService productService;

    @GetMapping("/checkQuantityInventory")
    private ResponseEntity<QuantityResponse> trackQuantity (@RequestParam("productName") String productName){
        return productService.trackInventory(productName);
    }
}
