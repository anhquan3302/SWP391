package com.example.securityl.serviceimpl;

import com.example.securityl.model.CartItem;
import com.example.securityl.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.Map;
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
}
