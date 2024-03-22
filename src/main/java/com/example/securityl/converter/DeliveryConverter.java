package com.example.securityl.converter;


import com.example.securityl.dtos.DeliveryDto;
import com.example.securityl.models.Delivery;
import org.springframework.stereotype.Component;

@Component

public class DeliveryConverter {
    public DeliveryDto toDTO(Delivery delivery) {
        return DeliveryDto.builder()
                .orderId(delivery.getOrder().getId())
                .deliveryStatus(delivery.getDeliveryStatus())
                .trackingNumber(delivery.getTrackingNumber())
                .build();
    }
}
