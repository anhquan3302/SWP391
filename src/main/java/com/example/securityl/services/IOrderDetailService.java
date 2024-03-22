//package com.example.securityl.services;
//
//
//
//import com.example.securityl.dtos.OrderDetailDto;
//import com.example.securityl.dtos.chartDto.SalesChartDTO;
//import com.example.securityl.dtos.chartDto.TopSellingProductDTO;
//import com.example.securityl.exceptions.DataNotFoundException;
//import com.example.securityl.models.OrderDetail;
//
//import java.util.List;
//
//public interface IOrderDetailService {
//    OrderDetail createOrderDetail(OrderDetailDto orderDetailDTO) throws Exception;
//    OrderDetail updateOrderDetail(Long id, OrderDetailDto orderDetailDTO)
//            throws DataNotFoundException;
//    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;
//    void deleteById(Long id);
//    List<OrderDetail> findByOrderId(Long orderId);
//    List<SalesChartDTO> getTotalAmountSoldByDate();
//    List<TopSellingProductDTO> findMostSoldProductsByDate();
//}
