package com.example.securityl.services;



import com.example.securityl.dtos.DeliveryDto;
import com.example.securityl.models.Enum.StatusDelivery;

import java.util.List;

public interface IDeliveryService {
    void updateDeliveryStatus(DeliveryDto deliveryDto);
    DeliveryDto getDeliveryByOrderId(Long orderId);
    List<DeliveryDto> getDeliveriesByStatus(StatusDelivery status);
}
