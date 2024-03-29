package com.example.securityl.controllers;


import com.example.securityl.Responses.OrderListResponse;
import com.example.securityl.Responses.OrderResponse;
import com.example.securityl.components.LocalizationUtils;
import com.example.securityl.converter.OrderConverter;
import com.example.securityl.dtos.OrderDto;
import com.example.securityl.dtos.analysis.*;
import com.example.securityl.models.Order;
import com.example.securityl.services.IOrderService;
import com.example.securityl.utills.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;
    private final LocalizationUtils localizationUtils;

//    @CrossOrigin
    @PostMapping("")
    public ResponseEntity<?> createOrder(
            @Valid @RequestBody OrderDto orderDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Order orderResponse = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(orderResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @CrossOrigin
    @GetMapping("")
    public ResponseEntity<OrderListResponse> getOrdersByKeyword(
            @RequestParam(defaultValue = "", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(value = "categoryIds", required = false) Long paymentStatusId
    ) {
        // Tạo Pageable từ thông tin trang và giới hạn
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                //Sort.by("createdAt").descending()
                Sort.by("id").ascending()
        );
        Page<OrderResponse> orderPage = orderService
                .getOrdersByKeyword(keyword, paymentStatusId, pageRequest)
                .map(OrderConverter::fromOrder);
        // Lấy tổng số trang
        int totalPages = orderPage.getTotalPages();
        List<OrderResponse> orderResponses = orderPage.getContent();
        return ResponseEntity.ok(OrderListResponse
                .builder()
                .orders(orderResponses)
                .totalPages(totalPages)
                .build());
    }

//    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable long id,
            @Valid @RequestBody OrderDto orderDTO) {

        try {
            Order order = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @CrossOrigin
    @GetMapping("/user/{user_id}") // Thêm biến đường dẫn "user_id"
    //GET http://localhost:8088/api/v1/orders/user/4
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId) {
        try {
            List<Order> orders = orderService.findByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@Valid @PathVariable("id") Long orderId) {
        try {
            Order existingOrder = orderService.getOrder(orderId);
            OrderResponse orderResponse = OrderConverter.fromOrder(existingOrder);
            return ResponseEntity.ok(orderResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@Valid @PathVariable Long id) {
        //xóa mềm => cập nhật trường active = false
        orderService.deleteOrder(id);
        return ResponseEntity.ok(localizationUtils.getLocalizedMessage(MessageKeys.DELETE_ORDER_SUCCESSFULLY));
    }

    @GetMapping("/total-order")
    public ResponseEntity<OrderStatsDTO> getOrderStats() {
        OrderStatsDTO orderStatsDTO = orderService.getOrderStats();
        return ResponseEntity.ok(orderStatsDTO);
    }

    @GetMapping("/total-revenue")
    public ResponseEntity<RevenueDTO> getRevenueStatistics() {
        RevenueDTO revenueDTO = orderService.getRevenueStatistics();
        return ResponseEntity.ok(revenueDTO);
    }

    @GetMapping("/total-revenue-day")
    public ResponseEntity<RevenueDayDTO> getTotalSales() {
        RevenueDayDTO totalSalesDTO = orderService.getTotalSales();
        String formattedTotalSalesToday = totalSalesDTO.getFormattedTotalSalesToday();
        String formattedTotalSalesYesterday = totalSalesDTO.getFormattedTotalSalesYesterday();
        totalSalesDTO.setTotalSalesToday(formattedTotalSalesToday != null ? Double.valueOf(formattedTotalSalesToday) : null);
        totalSalesDTO.setTotalSalesYesterday(formattedTotalSalesYesterday != null ? Double.valueOf(formattedTotalSalesYesterday) : null);
        return ResponseEntity.ok(totalSalesDTO);
    }

    @GetMapping("/count/{productId}")
    public ResponseEntity<OrderCountDto> countOrdersByProductId(@PathVariable Long productId) {
        Integer orderCount = orderService.countOrdersByProductId(productId);
        OrderCountDto orderCountDto = new OrderCountDto();
        orderCountDto.setProductId(productId);
        orderCountDto.setOrderCount(orderCount);
        return ResponseEntity.ok(orderCountDto);
    }

    @GetMapping("/revenue/{productId}")
    public ResponseEntity<ProductRevenueDto> getProductRevenue(@PathVariable Long productId) {
        Optional<Double> revenue = orderService.getProductRevenue(productId);
        if (revenue.isPresent()) {
            ProductRevenueDto productRevenueDto = new ProductRevenueDto();
            productRevenueDto.setTotalRevenue(revenue.get());
            productRevenueDto.setProductId(productId);
            return ResponseEntity.ok(productRevenueDto);
        } else {
            ProductRevenueDto productRevenueDto = new ProductRevenueDto();
            productRevenueDto.setTotalRevenue(0.0); // Set total revenue to 0
            productRevenueDto.setProductId(productId);
            return ResponseEntity.ok(productRevenueDto);
        }
    }
}
