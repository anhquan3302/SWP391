package com.example.securityl.controller;

import com.example.securityl.model.Voucher;
import com.example.securityl.request.VoucherRequest.VoucherRequest;
import com.example.securityl.response.ObjectResponse.ResponseObject;
import com.example.securityl.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupon")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('admin')")
public class VoucherController {
    private final VoucherService voucherService;

    @PostMapping()
    private ResponseEntity<ResponseObject> createVoucher(@RequestBody VoucherRequest voucherRequest){
        return voucherService.createVoucher(voucherRequest);
    }

    @GetMapping()
    private ResponseEntity<ResponseObject> getAll(){
        return  voucherService.findAllVoucher();
    }

    @DeleteMapping("{voucherId}")
    private ResponseEntity<ResponseObject> deleteVoucherById(@PathVariable Integer voucherId){
        voucherService.deleteVoucher(voucherId);
        return ResponseEntity.ok().body(new ResponseObject("Success","Delete success",null));
    }

    @GetMapping("/{voucherCode}")
    public ResponseEntity<ResponseObject> checkVoucherExistence(@PathVariable String voucherCode) {
        return voucherService.searchVoucher(voucherCode);
    }

    @PutMapping("/{voucherId}")
    public ResponseEntity<ResponseObject> updateVoucher(@PathVariable Integer voucherId,@RequestBody VoucherRequest voucherRequest){
        return voucherService.updateVoucher(voucherId,voucherRequest);
    }
}
