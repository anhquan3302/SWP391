package com.example.securityl.services;



import com.example.securityl.Responses.CouponResponse;

import java.util.List;

public interface ICouponService {
    double calculateCouponValue(String couponCode, double totalAmount);
    List<CouponResponse> getAllCoupon();
}
