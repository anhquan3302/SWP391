package com.example.securityl.service;

import com.example.securityl.request.CreateOrderStatusRequest;
import com.example.securityl.request.UpdateOrderStatusRequest;
import com.example.securityl.response.OrderStatusResponse;
import com.example.securityl.response.UpdateOrderStatusResponse;
import org.springframework.http.ResponseEntity;

public interface OrderStatusService {

    ResponseEntity<OrderStatusResponse> createOrderStatus(CreateOrderStatusRequest request);

    ResponseEntity<UpdateOrderStatusResponse> updateOrderStatus(Integer orderStatusId, UpdateOrderStatusRequest request);
}
