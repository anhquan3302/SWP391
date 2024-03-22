//package com.example.securityl.controllers;
//
//
//import com.example.securityl.dtos.DeliveryDto;
//import com.example.securityl.models.Enum.StatusDelivery;
//import com.example.securityl.services.IDeliveryService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/delivery")
//@RequiredArgsConstructor
//@CrossOrigin
//public class DeliveryController {
//    private final IDeliveryService deliveryService;
//
//    @PostMapping("/update")
//    public ResponseEntity<String> updateDeliveryStatus(@RequestBody DeliveryDto deliveryDto) {
//        try {
//            deliveryService.updateDeliveryStatus(deliveryDto);
//            return ResponseEntity.ok("Delivery status updated successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error updating delivery status: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/get/{orderId}")
//    public ResponseEntity<DeliveryDto> getDeliveryByOrderId(@PathVariable Long orderId) {
//        DeliveryDto deliveryDto = deliveryService.getDeliveryByOrderId(orderId);
//        return deliveryDto != null ?
//                ResponseEntity.ok(deliveryDto) :
//                ResponseEntity.notFound().build();
//    }
//
//    @GetMapping("/status/{status}")
//    public ResponseEntity<List<DeliveryDto>> getDeliveriesByStatus(@PathVariable StatusDelivery status) {
//        List<DeliveryDto> deliveries = deliveryService.getDeliveriesByStatus(status);
//        return ResponseEntity.ok(deliveries);
//    }
//}
