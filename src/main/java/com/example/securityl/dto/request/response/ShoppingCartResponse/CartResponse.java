package com.example.securityl.dto.request.response.ShoppingCartResponse;

import com.example.securityl.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {
    private String status;
    private String message;
    private CartItem payload;
}
