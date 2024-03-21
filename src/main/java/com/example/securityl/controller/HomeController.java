package com.example.securityl.controller;

import com.example.securityl.dto.request.response.OrderResponse.ListOrderResponse;
import com.example.securityl.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/", maxAge = 3600)
public class HomeController {

    private final OrderService orderService;








    @GetMapping("/getAllOrder")
    private ResponseEntity<ListOrderResponse> listOrder(){
        return  orderService.viewOder();
    }
}
