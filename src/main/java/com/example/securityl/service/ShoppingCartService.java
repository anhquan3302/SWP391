package com.example.securityl.service;

import ch.qos.logback.core.model.Model;
import com.example.securityl.model.CartItem;

import java.util.Collection;
import java.util.List;

public interface ShoppingCartService {
    void add(CartItem item);
    void remove(int id);
    CartItem updateCart(int productId, int quantity);

    void deleteCart();

    Collection<CartItem> getAllCart();
    double getTotal();

    int getCount();
}
