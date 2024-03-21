package com.example.securityl.serviceimpl;

import com.example.securityl.model.OrderStatus;
import com.example.securityl.repository.OrderStatusRepository;
import com.example.securityl.dto.request.CreateOrderStatusRequest;
import com.example.securityl.dto.request.UpdateOrderStatusRequest;
import com.example.securityl.dto.request.response.OrderStatusResponse;
import com.example.securityl.dto.request.response.UpdateOrderStatusResponse;
import com.example.securityl.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    @Override
    public ResponseEntity<OrderStatusResponse> createOrderStatus(CreateOrderStatusRequest request) {
        try {
            OrderStatus orderStatus = OrderStatus.builder()
                    .createDate(new Date())
                    .description(request.getDescription())
                    .name(request.getName())
                    .updateDate(new Date())
                    .build();
            OrderStatus savedOrderStatus = orderStatusRepository.save(orderStatus);
            OrderStatusResponse response = new OrderStatusResponse(savedOrderStatus.getOrderStatusId(),
                    savedOrderStatus.getCreateDate(),
                    savedOrderStatus.getDescription(),
                    savedOrderStatus.getName(),
                    savedOrderStatus.getUpdateDate());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @Override
    public ResponseEntity<UpdateOrderStatusResponse> updateOrderStatus(Integer orderStatusId, UpdateOrderStatusRequest request) { // Sử dụng kiểu Integer
        try {
            Optional<OrderStatus> optionalOrderStatus = orderStatusRepository.findById(orderStatusId);
            if (!optionalOrderStatus.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            OrderStatus orderStatus = optionalOrderStatus.get();
            orderStatus.setDescription(request.getDescription());
            orderStatus.setName(request.getName());
            orderStatus.setUpdateDate(new Date());

            OrderStatus updatedOrderStatus = orderStatusRepository.save(orderStatus);
            UpdateOrderStatusResponse response = new UpdateOrderStatusResponse("Success", "Order status updated successfully", updatedOrderStatus.getOrderStatusId(), updatedOrderStatus.getCreateDate(), updatedOrderStatus.getUpdateDate());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new UpdateOrderStatusResponse("Fail", "Failed to update order status", null, null, null));
        }
    }

    }


