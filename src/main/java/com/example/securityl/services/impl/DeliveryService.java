//package com.example.securityl.services.impl;
//
//
//import com.example.securityl.converter.DeliveryConverter;
//import com.example.securityl.dtos.DeliveryDto;
//import com.example.securityl.models.Delivery;
//import com.example.securityl.models.Enum.StatusDelivery;
//import com.example.securityl.models.Order;
//import com.example.securityl.repositories.DeliveryRepository;
//import com.example.securityl.services.IDeliveryService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class DeliveryService implements IDeliveryService {
//
//    private final DeliveryRepository deliveryRepository;
//    private final OrderService orderService;
//    private final DeliveryConverter deliveryConverter;
//
//    @Transactional
//    public void updateDeliveryStatus(DeliveryDto deliveryDto) {
//        Order order = orderService.getOrder(deliveryDto.getOrderId());
//
//        Delivery delivery = deliveryRepository.findByOrderId(deliveryDto.getOrderId())
//                .orElse(new Delivery());
//
//        delivery.setOrder(order);
//        delivery.setDeliveryStatus(deliveryDto.getDeliveryStatus());
//        delivery.setTrackingNumber(deliveryDto.getTrackingNumber());
//
//        deliveryRepository.save(delivery);
//    }
//
//    @Transactional(readOnly = true)
//    public DeliveryDto getDeliveryByOrderId(Long orderId) {
//        return deliveryRepository.findByOrderId(orderId)
//                .map(deliveryConverter::toDTO)
//                .orElse(null);
//    }
//
//    @Transactional(readOnly = true)
//    public List<DeliveryDto> getDeliveriesByStatus(StatusDelivery status) {
//        List<Delivery> deliveries = deliveryRepository.findByDeliveryStatus(status);
//        return deliveries.stream()
//                .map(deliveryConverter::toDTO)
//                .collect(Collectors.toList());
//    }
//
//}