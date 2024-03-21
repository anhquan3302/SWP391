package com.example.securityl.controller;

import com.example.securityl.dto.request.VoucherRequest.VoucherRequest;
import com.example.securityl.dto.request.response.ObjectResponse.ResponseObject;
import com.example.securityl.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupon")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','STAFF')")
@CrossOrigin(origins = "http://localhost:5173/", maxAge = 3600)
public class VoucherController {
    private final VoucherService voucherService;

    @PostMapping("/createVoucher")
    private ResponseEntity<ResponseObject> createVoucher(@RequestBody VoucherRequest voucherRequest){
        return voucherService.createVoucher(voucherRequest);
    }

    @GetMapping("/getAllVoucher")
    private ResponseEntity<ResponseObject> getAll(){
        return  voucherService.findAllVoucher();
    }

    @DeleteMapping("/deleteVoucher/{voucherId}")
    private ResponseEntity<ResponseObject> deleteVoucherById(@PathVariable Integer voucherId){
        voucherService.deleteVoucher(voucherId);
        return ResponseEntity.ok().body(new ResponseObject("Success","Delete success",null));
    }

    @GetMapping("/{voucherCode}")
    public ResponseEntity<ResponseObject> checkVoucherExistence(@PathVariable String voucherCode) {
        return voucherService.searchVoucher(voucherCode);
    }
}
