package com.example.securityl.serviceimpl;

import com.example.securityl.model.*;
import com.example.securityl.repository.OrderDetailRepository;
import com.example.securityl.repository.OrderRepository;
import com.example.securityl.repository.ProductRepository;
import com.example.securityl.repository.VoucherRepository;
import com.example.securityl.request.CheckoutResquest.CheckoutRequest;
import com.example.securityl.service.OrderService;
import com.example.securityl.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@SessionScope
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final VoucherRepository voucherRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ShoppingCartService shoppingCartService;
    @Override
    public Orders checkout(CheckoutRequest checkoutRequest, List<CartItem> cartItems) {
        Date date = new Date();
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        Orders order = Orders.builder()
                .phone(checkoutRequest.getPhone())
                .email(checkoutRequest.getEmail())
                .fullname(checkoutRequest.getFullname())
                .address(checkoutRequest.getAddress())
                .note(checkoutRequest.getNote())
                .history(date)
                .status(true)
                .build();
        orderRepository.save(order);
        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            Integer productId = cartItem.getProductId();
            Products product = productRepository.findById(productId).orElse(null);
            if (product == null) {
                throw new RuntimeException("Product with ID " + productId + " is not found");
            }
            int quantity = cartItem.getQuantity();
            double totalMoney = shoppingCartService.getTotal();
            OrderDetail orderDetail = OrderDetail.builder()
                    .product(product)
                    .fullname(product.getProductName())
                    .price(cartItem.getPrice())
                    .number(quantity)
                    .order(order)
                    .totalMoney(totalMoney)
                    .build();
            orderDetailRepository.save(orderDetail);
            totalPrice += totalMoney;
        }
        if (checkoutRequest.getVoucherCode() != null ) {
            Voucher voucher = voucherRepository.findByVoucherCode(checkoutRequest.getVoucherCode());
            if (voucher != null && voucher.isActive() && isVoucherValid(voucher)) {
                totalPrice -= calculateDiscount(totalPrice, voucher);
            }
        }
        order.setTotalMoney(totalPrice);
        return order;
    }

    private double calculateDiscount(double totalPrice, Voucher voucher) {
        double discount = 0;
        if (voucher.isActive() && isVoucherValid(voucher)) {
            if (voucher.getDiscountPercentage() > 0) {
                discount = totalPrice * (voucher.getDiscountPercentage() / 100.0);
            } else {
            }
        }
        return discount;
    }

    private boolean isVoucherValid(Voucher voucher) {
        return voucher.isActive();
    }

}


