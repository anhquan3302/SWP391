package com.example.securityl.dto.request.response.OrderResponse;

import com.example.securityl.model.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListOrderResponse {
    private String status;
    private String message;
    private List<Orders> orderResponseList;
}
