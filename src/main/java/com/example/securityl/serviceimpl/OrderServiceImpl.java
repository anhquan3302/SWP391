package com.example.securityl.serviceimpl;

import com.example.securityl.model.CartItem;
import com.example.securityl.model.OrderDetail;
import com.example.securityl.model.Orders;
import com.example.securityl.model.Product;
import com.example.securityl.repository.OrderDetailRepository;
import com.example.securityl.repository.OrderRepository;
import com.example.securityl.repository.ProductRepository;
import com.example.securityl.dto.request.CheckoutResquest.CheckoutRequest;
import com.example.securityl.dto.request.response.ObjectResponse.ResponseObject;
import com.example.securityl.dto.request.response.OrderResponse.ListOrderResponse;
import com.example.securityl.dto.request.response.OrderResponse.ObjectRepose;
import com.example.securityl.dto.request.response.OrderResponse.OrderResponse;
import com.example.securityl.service.OrderService;
import com.example.securityl.service.ShoppingCartService;
import com.example.securityl.service.VoucherService;
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
    private final VoucherService voucherService;
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
                .code(checkoutRequest.getCode())
                .custormer(checkoutRequest.getFullname())
                .address(checkoutRequest.getAddress())
                .note(checkoutRequest.getNote())
                .history(date)
                .status("Dang cho xu ky")
                .build();
        double totalPrice = 0;

        // apply voucher vào nếu có
        if (checkoutRequest.getVoucherCode() != null) {
            ResponseEntity<ResponseObject> voucherResponse = voucherService.applyVoucher(checkoutRequest.getVoucherCode());
            if (voucherResponse.getStatusCode().is2xxSuccessful()) {
                totalPrice = (double) voucherResponse.getBody().getPayload();
            } else {
                throw new RuntimeException("Failed to apply voucher: " + voucherResponse.getBody().getMessage());
            }
        } else {
            // Nếu không có voucher, sử dụng tổng giá tiền của giỏ hàng
            totalPrice = shoppingCartService.getTotal();
        }

        // Thiết lập tổng giá tiền trong đơn hàng
        order.setTotalMoney(totalPrice);
        orderRepository.save(order);

        // Lưu thông tin chi tiết đơn hàng
        for (CartItem cartItem : cartItems) {
            Integer productId = cartItem.getProductId();
            Product product = productRepository.findById(productId).orElse(null);
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




    @Override
    public ResponseEntity<ListOrderResponse> viewOder() {
        List<Orders> list = orderRepository.findAll();

        return ResponseEntity.ok().body(new ListOrderResponse("Sucees","List Order",list));
    }

    @Override
    public ResponseEntity<ObjectRepose> getInfor(Integer orderId) {
        Orders orders = orderRepository.findOrdersByOrderId(orderId);
        if(orders != null){
            return ResponseEntity.ok().body(new ObjectRepose("Success","InforOrder",convertToOrderResponse(orders)));
        }
        return ResponseEntity.badRequest().body(new ObjectRepose("Fail","Not found order",null));
    }






    private OrderResponse convertToOrderResponse(Orders orderRequest) {
        return OrderResponse.builder()
                .orderId(orderRequest.getOrderId())
                .phone(orderRequest.getPhone())
                .email(orderRequest.getEmail())
                .code(orderRequest.getCode())
                .address(orderRequest.getAddress())
                .totalMoney(orderRequest.getTotalMoney())
                .history(orderRequest.getHistory())
                .note(orderRequest.getNote())
                .build();
    }





}


