package com.example.securityl.service;

import com.example.securityl.model.CartItem;
import com.example.securityl.model.Orders;
import com.example.securityl.request.CheckoutResquest.CheckoutRequest;
import com.example.securityl.response.ObjectResponse.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    Orders checkout(CheckoutRequest cartItems, List<CartItem> checkoutRequest);
}
