package com.example.securityl.dto.request.response.Inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuantityResponse {
    private String productName;
    private Integer quantity;
}
