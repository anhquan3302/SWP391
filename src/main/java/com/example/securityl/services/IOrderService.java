//package com.example.securityl.services;
//
//
//import com.example.securityl.dtos.OrderDto;
//import com.example.securityl.dtos.analysis.OrderStatsDTO;
//import com.example.securityl.dtos.analysis.RevenueDTO;
//import com.example.securityl.dtos.analysis.RevenueDayDTO;
//import com.example.securityl.exceptions.DataNotFoundException;
//import com.example.securityl.models.Order;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface IOrderService {
//    Order createOrder(OrderDto orderDto) throws Exception;
//    Page<Order> getOrdersByKeyword(String keyword, Long paymentStatusId,Pageable pageable);
//    Order updateOrder(Long id, OrderDto orderDTO) throws DataNotFoundException;
//    Order getOrder(Long id);
//    void deleteOrder(Long id);
//    List<Order> findByUserId(Long userId);
//
//    OrderStatsDTO getOrderStats();
//
//    RevenueDTO getRevenueStatistics();
//    RevenueDayDTO getTotalSales();
//    Integer countOrdersByProductId(Long productId);
//    Optional<Double> getProductRevenue(Long productId);
//}
