//package com.example.securityl.controllers;
//
//
//import com.example.securityl.Responses.OrderDetailResponse;
//import com.example.securityl.components.LocalizationUtils;
//import com.example.securityl.converter.OrderDetailConverter;
//import com.example.securityl.dtos.OrderDetailDto;
//import com.example.securityl.dtos.chartDto.SalesChartDTO;
//import com.example.securityl.dtos.chartDto.TopSellingProductDTO;
//import com.example.securityl.exceptions.DataNotFoundException;
//import com.example.securityl.models.OrderDetail;
//import com.example.securityl.services.IOrderDetailService;
//import com.example.securityl.utills.MessageKeys;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("${api.prefix}/orders-detail")
//@RequiredArgsConstructor
//@CrossOrigin
//public class OrderDetailController {
//    private final IOrderDetailService orderDetailService;
//    private final LocalizationUtils localizationUtils;
//
//    @PostMapping("")
//    public ResponseEntity<?> createOrderDetail(
//            @Valid @RequestBody OrderDetailDto orderDetailDto,
//            BindingResult result
//    ) {
//        try {
//            if (result.hasErrors()) {
//                List<String> errorMessages = result.getFieldErrors()
//                        .stream()
//                        .map(FieldError::getDefaultMessage)
//                        .toList();
//                return ResponseEntity.badRequest().body(errorMessages);
//            }
//            OrderDetail orderDetailResponse = orderDetailService.createOrderDetail(orderDetailDto);
//            return ResponseEntity.ok(orderDetailResponse);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getOrderDetail(
//            @Valid @PathVariable("id") Long id) throws DataNotFoundException {
//        OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
//        return ResponseEntity.ok().body(OrderDetailConverter.fromOrderDetail(orderDetail));
//    }
//
//    @GetMapping("/order/{orderId}")
//    public ResponseEntity<?> getOrderDetails(
//            @Valid @PathVariable("orderId") Long orderId
//    ) {
//        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);
//        List<OrderDetailResponse> orderDetailResponses = orderDetails
//                .stream()
//                .map(OrderDetailConverter::fromOrderDetail)
//                .toList();
//        return ResponseEntity.ok(orderDetailResponses);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateOrderDetail(
//            @Valid @PathVariable("id") Long id,
//            @RequestBody OrderDetailDto orderDetailDTO) {
//        try {
//            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, orderDetailDTO);
//            return ResponseEntity.ok().body(orderDetail);
//        } catch (DataNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteOrderDetail(
//            @Valid @PathVariable("id") Long id) {
//        orderDetailService.deleteById(id);
//        return ResponseEntity.ok()
//                .body(localizationUtils
//                        .getLocalizedMessage(MessageKeys.DELETE_ORDER_DETAIL_SUCCESSFULLY));
//    }
//
//    @GetMapping("/chart")
//    public ResponseEntity<List<SalesChartDTO>> getTotalAmountSoldByDate() {
//        List<SalesChartDTO> totalAmounts = orderDetailService.getTotalAmountSoldByDate();
//        return ResponseEntity.ok(totalAmounts);
//    }
//
//    @GetMapping("/chart-top-sale-product")
//    public List<TopSellingProductDTO> getMostSoldProductsByDate() {
//        return orderDetailService.findMostSoldProductsByDate();
//    }
//}
