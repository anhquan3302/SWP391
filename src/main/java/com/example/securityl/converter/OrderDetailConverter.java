package com.example.securityl.converter;


import com.example.securityl.Responses.OrderDetailResponse;
import com.example.securityl.models.OrderDetail;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailConverter {
    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail){
        return OrderDetailResponse
                .builder()
                .quantity(orderDetail.getQuantity())
                .price(orderDetail.getPrice())
                .totalAmount(orderDetail.getTotalAmount())
                .discount(orderDetail.getDiscount())
                .product(orderDetail.getProduct().getId())
                .orders(orderDetail.getOrders().getId())
                .build();
    }
}
