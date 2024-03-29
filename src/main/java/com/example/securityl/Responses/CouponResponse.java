package com.example.securityl.Responses;


import com.example.securityl.models.CouponCondition;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponResponse {
    private Long id;

    private String code;

    private boolean active;

    private List<CouponCondition> couponCondition;

}
