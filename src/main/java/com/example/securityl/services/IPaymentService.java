package com.example.securityl.services;


import com.example.securityl.models.PaymentStatus;

import java.util.List;

public interface IPaymentService {
    List<PaymentStatus> getAll();
}
