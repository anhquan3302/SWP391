package com.example.securityl.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private int quantity;
    private double price;
    @JsonProperty("total_amount")
    private double totalAmount;

    private double discount;

    private Long product;

    private Long orders;
}
