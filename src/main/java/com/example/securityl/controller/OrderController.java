package com.example.securityl.controller;

import com.example.securityl.response.OrderResponse.ListOrderResponse;
import com.example.securityl.response.OrderResponse.ObjectRepose;
import com.example.securityl.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
@PreAuthorize("hasAnyRole('deliverystaff')")
public class OrderController {
    private final OrderService orderService;
    @GetMapping("/get-orders")
    private ResponseEntity<ObjectRepose> getInFor(@RequestParam("orderId")Integer orderId){
        return orderService.getInfor(orderId);
    }

    @GetMapping("/getallorders")
    private ResponseEntity<ListOrderResponse> listOrder(){
        return  orderService.viewOder();
    }
}
