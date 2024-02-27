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

        // Tạo đơn hàng mới
        Orders order = Orders.builder()
                .phone(checkoutRequest.getPhone())
                .email(checkoutRequest.getEmail())
                .fullname(checkoutRequest.getFullname())
                .address(checkoutRequest.getAddress())
                .note(checkoutRequest.getNote())
                .history(date)
                .status(true)
                .build();

        // Tính tổng giá trị đơn hàng
        double totalPrice = shoppingCartService.getTotal();

        // Kiểm tra và áp dụng mã giảm giá
        if (checkoutRequest.getVoucherCode() != null) {
            Voucher voucher = voucherRepository.findByVoucherCode(checkoutRequest.getVoucherCode());
            if (voucher != null && voucher.isActive()) {
                totalPrice = calculateDiscount(totalPrice, voucher);
                voucher.setActive(false);
                voucherRepository.save(voucher);
            } else {
                throw new RuntimeException("Mã voucher không hợp lệ hoặc đã hết hạn");
            }
        }

        // Lưu tổng giá trị đơn hàng vào đơn hàng
        order.setTotalMoney(totalPrice);
        orderRepository.save(order);

        // Lưu chi tiết đơn hàng
        for (CartItem cartItem : cartItems) {
            Integer productId = cartItem.getProductId();
            Products product = productRepository.findById(productId).orElse(null);
            if (product == null) {
                throw new RuntimeException("Product with ID " + productId + " is not found");
            }
            int quantity = cartItem.getQuantity();
            double totalMoney = cartItem.getPrice() * quantity;
            OrderDetail orderDetail = OrderDetail.builder()
                    .product(product)
                    .fullname(product.getProductName())
                    .price(cartItem.getPrice())
                    .number(quantity)
                    .order(order)
                    .totalMoney(totalMoney)
                    .build();
            orderDetailRepository.save(orderDetail);
        }

        return order;
    }

    private double calculateDiscount(double totalPrice, Voucher voucher) {
        double discountPercentage = voucher.getDiscountPercentage();
        if (totalPrice >= 10000) {
            discountPercentage = 20;
        } else if (totalPrice >= 5000) {
            discountPercentage = 10;
        } else {
            discountPercentage = 0;
        }

        double discountAmount = (totalPrice * discountPercentage) / 100;
        return totalPrice - discountAmount;
    }









}


