package com.example.securityl.dtos;


import com.example.securityl.models.Enum.StatusDelivery;
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
