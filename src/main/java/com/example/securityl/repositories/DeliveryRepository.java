package com.example.securityl.repositories;


import com.example.securityl.models.Delivery;
import com.example.securityl.models.Enum.StatusDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findByOrderId(Long orderId);

    List<Delivery> findByDeliveryStatus(StatusDelivery status);
}
