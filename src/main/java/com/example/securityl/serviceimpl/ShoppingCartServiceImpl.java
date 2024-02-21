package com.example.securityl.serviceimpl;

import ch.qos.logback.core.model.Model;
import com.example.securityl.model.CartItem;
import com.example.securityl.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;

@SessionScope
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    Map<Integer, CartItem> maps = new HashMap<>();

    @Override
    public void add(CartItem item) {
        CartItem cartItem = maps.get(item.getProductId());
        if(cartItem == null){
            maps.put(item.getProductId(), item);
        }else {
            cartItem.setQuantity(item.getQuantity() +1);
        }
    }

    @Override
    public void remove(int id) {
        maps.remove(id);
    }

    @Override
    public CartItem updateCart(int productId, int quantity) {
        CartItem cartItem = maps.get(productId);
        cartItem.setQuantity(quantity);
        return cartItem;
    }

    @Override
    public void deleteCart() {
        maps.clear();
    }

    @Override
    public Collection<CartItem> getAllCart() {
        return maps.values();
    }


    @Override
    public double getTotal() {
        return maps.values().stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
    }

    @Override
    public int getCount() {
        return maps.values().size();
    }


}
