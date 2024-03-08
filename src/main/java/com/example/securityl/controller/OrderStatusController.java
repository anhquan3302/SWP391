package com.example.securityl.controller;

import com.example.securityl.dto.request.CreateOrderStatusRequest;
import com.example.securityl.dto.request.UpdateOrderStatusRequest;
import com.example.securityl.dto.request.response.OrderStatusResponse;

import com.example.securityl.dto.request.response.UpdateOrderStatusResponse;
import com.example.securityl.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/OrderStatus")
public class OrderStatusController {
    private final OrderStatusService orderStatusService;

    @PostMapping("/createorderStatus")
    public ResponseEntity<OrderStatusResponse> createOrderStatus(@RequestBody CreateOrderStatusRequest request) {
        return orderStatusService.createOrderStatus(request);
    }
    @PutMapping("/updateorderStatus")
    public ResponseEntity<UpdateOrderStatusResponse> updateOrderStatus(@RequestParam Integer orderStatusId, @RequestBody UpdateOrderStatusRequest request) {
        return orderStatusService.updateOrderStatus(orderStatusId, request);
    }
}
