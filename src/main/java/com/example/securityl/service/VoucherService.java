package com.example.securityl.service;

import com.example.securityl.model.Voucher;
import com.example.securityl.request.VoucherRequest.VoucherRequest;
import com.example.securityl.response.ObjectResponse.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface VoucherService {
    ResponseEntity<ResponseObject> createVoucher(VoucherRequest voucherRequest);

    ResponseEntity<ResponseObject> findAllVoucher();



    Voucher deleteVoucher(Integer voucherId);
}
