package com.example.securityl.repository;

import com.example.securityl.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {
    Orders findOrdersByOrderId(Integer orderId);
}
