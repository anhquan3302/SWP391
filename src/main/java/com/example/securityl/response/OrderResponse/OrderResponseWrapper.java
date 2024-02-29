package com.example.securityl.response.OrderResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseWrapper {
    private String status;
    private String message;
    private OrderResponse orderResponse;
}
