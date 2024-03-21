package com.example.securityl.serviceimpl;

import com.example.securityl.model.*;
import com.example.securityl.repository.ProductRepository;
//import com.example.securityl.request.CheckoutResquest.ShoppingRequest;
import com.example.securityl.dto.request.CheckoutResquest.ShoppingRequest;
import com.example.securityl.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Component
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    Map<Integer, CartItem> maps = new HashMap<>();

    private final ProductRepository productRepository;
    @Override
    public void remove(int id) {
        maps.remove(id);
    }

    @Override
    public CartItem updateCart(int productId, int quantity) {
        CartItem cartItem = maps.get(productId);
        var product = productRepository.findProductByProductId(productId).orElse(null);
        assert product != null;
        Integer availableQuantity = productRepository.findQuantityById(productId);
        if ( product.getQuantity() <= availableQuantity) {
            cartItem.setQuantity(quantity);
        } else {
            throw new RuntimeException("Quantity is not existed ");
        }
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



    @Override
    public List<CartItem> addToCart(List<ShoppingRequest> shoppingRequests) {
        List<CartItem> cartItems = new ArrayList<>();
        for (ShoppingRequest shoppingRequest : shoppingRequests) {
            Integer productId = shoppingRequest.getProductId();
            Optional<Product> productOpt = productRepository.findById(productId);
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                CartItem cartItem = new CartItem();
                cartItem.setProductId(product.getProductId());
                cartItem.setProductName(product.getProductName());
                cartItem.setPrice(product.getPrice());
                cartItem.setQuantity(shoppingRequest.getQuantity());
                cartItem.setDiscount(product.getDiscount());
                maps.put(productId, cartItem);
                cartItems.add(cartItem);
            } else {
                throw new RuntimeException("Product with ID " + productId + " not found");
            }
        }
        return cartItems;
    }




}
