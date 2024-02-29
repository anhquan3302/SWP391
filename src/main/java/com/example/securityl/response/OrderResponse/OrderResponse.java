package com.example.securityl.response.OrderResponse;

import com.example.securityl.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Integer orderId;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private String note;
    private boolean status;
    private Double totalMoney;
    private Date history;
    private OrderStatus orderStatus;
}
