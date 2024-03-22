package com.example.securityl.repositories;


import com.example.securityl.models.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findAllByUserId(Pageable pageable, Long userId);
}
