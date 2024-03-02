package com.example.securityl.service;

import com.example.securityl.model.CartItem;
import com.example.securityl.model.Orders;
import com.example.securityl.request.CheckoutResquest.CheckoutRequest;
import com.example.securityl.response.OrderResponse.ListOrderResponse;
import com.example.securityl.response.OrderResponse.ObjectRepose;
import com.example.securityl.response.OrderResponse.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    Orders checkout(CheckoutRequest cartItems, List<CartItem> checkoutRequest);

    ResponseEntity<ListOrderResponse> viewOder();

    ResponseEntity<ObjectRepose> getInfor(Integer orderId);
}
