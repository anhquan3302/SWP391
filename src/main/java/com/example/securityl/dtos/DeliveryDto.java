package com.example.securityl.dtos;

import com.eFurnitureproject.eFurniture.models.Enum.StatusDelivery;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryDto {
    private Long orderId;
    private StatusDelivery deliveryStatus;
    private String trackingNumber;
}
