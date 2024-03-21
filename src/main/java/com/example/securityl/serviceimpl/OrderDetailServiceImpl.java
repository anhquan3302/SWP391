package com.example.securityl.serviceimpl;

import com.example.securityl.model.OrderDetail;
import com.example.securityl.repository.OrderDetailRepository;
import com.example.securityl.repository.OrderRepository;
import com.example.securityl.repository.ProductRepository;
import com.example.securityl.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderDetail createOrderDetail(OrderDetail orderDetailDTO) {
        // Retrieve the order based on its ID from the order repository
        var order = orderRepository.findById(orderDetailDTO.getOrderDetailId());

        // Retrieve the product based on its ID from the product repository
        var product = productRepository.findById(orderDetailDTO.getProduct());
        OrderDetail orderDetail = new OrderDetail();

        // Save the order detail to the database using the order detail repository
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public OrderDetail updateOrderDetail(Integer id, OrderDetail orderDetailDTO) {
        return null;
    }
}
