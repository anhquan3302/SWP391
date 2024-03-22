package com.example.securityl.dtos.chartDto;


import com.example.securityl.models.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopSellingProductDTO {
    private Product product;
    private int totalSold;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
}
