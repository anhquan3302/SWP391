package com.example.securityl.response.PaymentResDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse implements Serializable {
    private String status;
    private String message;
    private String URL;
}
