package com.example.securityl.converter;


import com.example.securityl.Responses.CouponResponse;
import com.example.securityl.models.Coupon;
import org.springframework.stereotype.Component;

@Component
public class CouponConverter {
    public static CouponResponse fromCoupon(Coupon coupon) {
        CouponResponse couponResponse = CouponResponse
                .builder()
                .id(coupon.getId())
                .code(coupon.getCode())
                .active(coupon.isActive())
                .couponCondition(coupon.getCouponCondition())
                .build();
        return couponResponse;
    }
}
