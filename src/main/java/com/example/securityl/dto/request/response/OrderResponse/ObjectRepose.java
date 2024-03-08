package com.example.securityl.dto.request.response.OrderResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObjectRepose {
    private String status;
    private String message;
    private OrderResponse orderResponse;
}
