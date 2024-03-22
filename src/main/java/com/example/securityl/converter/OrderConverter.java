package com.example.securityl.converter;


import com.example.securityl.Responses.OrderResponse;
import com.example.securityl.models.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {
    public static OrderResponse fromOrder(Order order) {
        OrderResponse orderResponse =  OrderResponse
                .builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .active(order.getActive())
                .address(order.getAddress())
                .province(order.getProvince())
                .district(order.getDistrict())
                .ward(order.getWard())
                .phoneNumber(order.getPhoneNumber())
                .email(order.getEmail())
                .fullName(order.getFullName())
                .discounts(order.getDiscounts())
                .notes(order.getNotes())
                .shippingDate(order.getShippingDate())
                .shippingMethod(order.getShippingMethod())
                .paymentMethod(order.getPaymentMethod())
                .totalAmount(order.getTotalAmount())
                .subTotal(order.getSubTotal())
                .orderStatus(order.getOrderStatus())
                .paymentStatus(order.getPaymentStatus())
                .orderDetails(order.getOrderDetails())
                .build();
        Long couponId = (order.getCoupon() != null) ? order.getCoupon().getId() : null;
        orderResponse.setCouponId(couponId);
        return orderResponse;
    }
}
