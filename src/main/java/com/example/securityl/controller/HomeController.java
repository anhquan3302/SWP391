package com.example.securityl.controller;

import com.example.securityl.request.ProductRequest.SearchProduct;
import com.example.securityl.response.OrderResponse.ListOrderResponse;
import com.example.securityl.response.ProductResponse.ListProductResponse;
import com.example.securityl.response.ProductResponse.ProductResponse;
import com.example.securityl.service.OrderService;
import com.example.securityl.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class HomeController {

    private final OrderService orderService;








    @GetMapping("/getAllOrder")
    private ResponseEntity<ListOrderResponse> listOrder(){
        return  orderService.viewOder();
    }
}
