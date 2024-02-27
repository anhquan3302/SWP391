package com.example.securityl.service;

import com.example.securityl.model.CartItem;
//import com.example.securityl.request.CheckoutResquest.ShoppingRequest;


import java.util.Collection;
import java.util.List;

public interface ShoppingCartService {

    void remove(int id);
    CartItem updateCart(int productId, int quantity);

    void deleteCart();

    Collection<CartItem> getAllCart();
    double getTotal();

    int getCount();


//    List<CartItem> addToCart(List<ShoppingRequest> shoppingRequest);
}
