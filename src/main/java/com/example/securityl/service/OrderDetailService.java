package com.example.securityl.service;

import com.example.securityl.model.OrderDetail;

public interface OrderDetailService {
    OrderDetail createOrderDetail(OrderDetail orderDetailDto);

    void deleteById(Integer id);

    OrderDetail updateOrderDetail(Integer id, OrderDetail orderDetailDTO);
}
