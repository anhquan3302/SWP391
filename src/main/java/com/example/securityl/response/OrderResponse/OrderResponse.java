package com.example.securityl.response.OrderResponse;


import com.example.securityl.model.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private String status;
    private String message;
    private Orders order;
}
