package com.example.securityl.converter;

import com.example.securityl.dto.request.response.OrderResponse.OrderResponse;
import com.example.securityl.model.Orders;
import jakarta.persistence.criteria.Order;

public class OrderConverter {

    public static OrderResponse fromOrder(Orders order) {
        OrderResponse orderResponse =  OrderResponse
                .builder()
                .orderId(order.getOrderId())
                .customer(String.valueOf(order.getUser().getUserId()))
                .note(order.getStatus())
                .address(order.getAddress())
                .phone(order.getPhone())
                .email(order.getEmail())
                .history(order.getHistory())
                .note(order.getNote())
                .totalMoney(order.getTotalMoney())
                .status(order.getStatus())
                .status(order.getStatus())
                .build();
        return orderResponse;
    }

}
