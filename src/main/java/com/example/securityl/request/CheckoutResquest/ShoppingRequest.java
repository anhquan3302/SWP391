package com.example.securityl.request.CheckoutResquest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingRequest {
    private Integer productId;
    private String productName;
    private double price;
    private Integer quantity;
    private double discount;


}
