package com.example.securityl.services.impl;


import com.example.securityl.models.PaymentStatus;
import com.example.securityl.repositories.PaymentStatusRepository;
import com.example.securityl.services.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {
    private final PaymentStatusRepository paymentStatusRepository;
    @Override
    public List<PaymentStatus> getAll() {
        return paymentStatusRepository.findAll();
    }
}
