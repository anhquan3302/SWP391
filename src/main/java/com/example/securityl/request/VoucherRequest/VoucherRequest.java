package com.example.securityl.request.VoucherRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoucherRequest {
    private String voucherCode;
    private Date startDate;
    private Date endDate;
    private boolean active;
    private double totalPrice;
}
