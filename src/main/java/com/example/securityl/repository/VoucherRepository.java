package com.example.securityl.repository;

import com.example.securityl.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher,Integer> {
    Voucher findByVoucherCode(String voucherCode);
}
