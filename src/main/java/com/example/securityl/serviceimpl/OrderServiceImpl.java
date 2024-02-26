package com.example.securityl.serviceimpl;

import com.example.securityl.model.*;
import com.example.securityl.repository.OrderDetailRepository;
import com.example.securityl.repository.OrderRepository;
import com.example.securityl.repository.ProductRepository;
import com.example.securityl.repository.VoucherRepository;
import com.example.securityl.request.CheckoutResquest.CheckoutRequest;
import com.example.securityl.response.ObjectResponse.ResponseObject;
import com.example.securityl.service.OrderService;
import com.example.securityl.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

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
        double totalPrice = shoppingCartService.getTotal();
        boolean voucherApplied = false;
        if (checkoutRequest.getVoucherCode() != null) {
            Voucher voucher = voucherRepository.findByVoucherCode(checkoutRequest.getVoucherCode());
            if (voucher != null && voucher.isActive()) {
                if (totalPrice >= 1000000) { // Nếu tổng giá trị đơn hàng >= 1,000,000
                    totalPrice -= calculateDiscount(totalPrice, voucher);
                    voucherApplied = true;
                    voucherRepository.save(voucher);
                } else if (totalPrice >= 500000) { // Nếu tổng giá trị đơn hàng >= 500,000 và < 1,000,000
                    totalPrice -= calculateDiscount(totalPrice, voucher);
                    voucherApplied = true;
                    voucherRepository.save(voucher);
                } else {
                    throw new RuntimeException("Voucher không đủ điều kiện áp dụng");
                }
            } else {
                throw new RuntimeException("Mã voucher không hợp lệ hoặc đã hết hạn");
            }
        }
        if (!voucherApplied) {
            order.setTotalMoney(totalPrice);
            return order;
        }
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
            totalPrice += totalMoney;
            orderDetailRepository.save(orderDetail);
        }
        return order;
    }

    private double calculateDiscount(double totalPrice, Voucher voucher) {
        double discountofVoucher = voucher.getDiscountPercentage();
        if (voucher.isActive()) {
            discountofVoucher = (totalPrice * discountofVoucher) / 100;
            voucher.setActive(false);
            voucherRepository.save(voucher);
        }
        return totalPrice - discountofVoucher;
    }







}


