package com.example.securityl.serviceimpl;

import ch.qos.logback.core.model.Model;
import com.example.securityl.model.*;
import com.example.securityl.repository.OrderRepository;
import com.example.securityl.repository.ProductRepository;
import com.example.securityl.repository.VoucherRepository;
import com.example.securityl.request.CheckoutResquest.CheckoutRequest;
import com.example.securityl.service.ShoppingCartService;
import jakarta.persistence.criteria.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;

@SessionScope
@Service
@Component
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    Map<Integer, CartItem> maps = new HashMap<>();


    private final ProductRepository productRepository;

    @Override
    public void add(CartItem item) {
        CartItem cartItem = maps.get(item.getProductId());
        if(cartItem == null){
            maps.put(item.getProductId(), item);

        }else {
            cartItem.setQuantity(item.getQuantity());
        }
    }

    @Override
    public void remove(int id) {
        maps.remove(id);
    }

    @Override
    public CartItem updateCart(int productId, int quantity) {
        CartItem cartItem = maps.get(productId);
        var product = productRepository.findProductByProductId(productId).orElse(null);
        cartItem.setQuantity(quantity);
        if(cartItem.getQuantity() <= 0){
            throw new RuntimeException("Vui long dien lai so luong > 0");
        }
        double totalPrice = product.getPrice() * quantity;
        cartItem.setPrice(totalPrice);
        return cartItem;
    }

    @Override
    public void deleteCart() {
        maps.clear();
    }

    @Override
    public Collection<CartItem> getAllCart() {
        List<CartItem> cartItems = new ArrayList<>(maps.values());

        return cartItems;
    }


    @Override
    public double getTotal() {
        double total = 0;
        for (CartItem item : maps.values()) {
            int quantity = item.getQuantity();
            double price = item.getPrice();
            double discount = (price *item.getDiscount())/100;
            total += quantity * (price - discount);
        }
        return total;
    }

    @Override
    public int getCount() {
        return maps.values().size();
    }





}
