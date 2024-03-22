//package com.example.securityl.controllers;
//
//
//import com.example.securityl.models.OrderStatus;
//import com.example.securityl.services.IOrderStatusOrderService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("${api.prefix}/orders-status")
//@RequiredArgsConstructor
//@CrossOrigin
//public class OrderStatusController {
//    private final IOrderStatusOrderService orderStatusOrderService;
//    @GetMapping("")
//    public ResponseEntity<List<OrderStatus>> getAll(){
//        List<OrderStatus> orderStatus = orderStatusOrderService.getAll();
//        return ResponseEntity.ok(orderStatus);
//    }
//}
