package com.example.securityl.service;

import com.example.securityl.model.CartItem;
import com.example.securityl.model.Orders;
import com.example.securityl.dto.request.CheckoutResquest.CheckoutRequest;
import com.example.securityl.dto.request.response.OrderResponse.ListOrderResponse;
import com.example.securityl.dto.request.response.OrderResponse.ObjectRepose;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    Orders checkout(CheckoutRequest cartItems, List<CartItem> checkoutRequest);

    ResponseEntity<ListOrderResponse> viewOder();

    ResponseEntity<ObjectRepose> getInfor(Integer orderId);
}
