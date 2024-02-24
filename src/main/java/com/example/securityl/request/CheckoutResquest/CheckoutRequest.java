package com.example.securityl.request.CheckoutResquest;

import com.example.securityl.model.CartItem;
import com.example.securityl.model.User;
import com.example.securityl.model.Voucher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutRequest {
    private CartItem cartItem;
    private String email;
    private String fullname;
    private String phone;
    private String address;
    private String note;
    private Date history;
    private String voucherCode;
}
