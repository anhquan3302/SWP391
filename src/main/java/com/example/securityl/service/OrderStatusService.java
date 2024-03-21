package com.example.securityl.service;

import com.example.securityl.dto.request.CreateOrderStatusRequest;
import com.example.securityl.dto.request.UpdateOrderStatusRequest;
import com.example.securityl.dto.request.response.OrderStatusResponse;
import com.example.securityl.dto.request.response.UpdateOrderStatusResponse;
import org.springframework.http.ResponseEntity;

public interface OrderStatusService {

    ResponseEntity<OrderStatusResponse> createOrderStatus(CreateOrderStatusRequest request);

    ResponseEntity<UpdateOrderStatusResponse> updateOrderStatus(Integer orderStatusId, UpdateOrderStatusRequest request);
}
