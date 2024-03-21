package com.example.securityl.dto.request.analysis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenueWeekDTO {
    private Double lastWeekRevenue;
    private Double currentWeekRevenue;
}
