package com.example.securityl.Responses;


import com.example.securityl.models.Address;
import com.example.securityl.models.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailResponse {
    private UserResponse userdetail;
    private List<Order> order;
    private Address address;
}
