package com.example.securityl.dtos.analysis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatsDTO {
    private long totalOrders;
    private double orderChangePercentage;

}
