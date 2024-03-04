package com.example.securityl.serviceimpl;

import com.example.securityl.model.Enum.Role;
import com.example.securityl.model.Voucher;
import com.example.securityl.repository.UserRepository;
import com.example.securityl.repository.VoucherRepository;
import com.example.securityl.request.VoucherRequest.VoucherRequest;
import com.example.securityl.response.ObjectResponse.ResponseObject;
import com.example.securityl.service.JwtService;
import com.example.securityl.service.UserService;
import com.example.securityl.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ResponseObject> createVoucher(VoucherRequest voucherRequest) {
        Voucher voucher;
        try {
            String token = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                    .getRequest()
                    .getHeader("Authorization")
                    .substring(7);
            String userEmail = jwtService.extractUsername(token);
            var requester = userRepository.findUserByEmail(userEmail).orElse(null);
            if (requester == null || !(requester.getRole().equals(Role.staff) || requester.getRole().equals(Role.admin))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject("Fail", "Unauthorized", null));
            }
            LocalDate startDate = voucherRequest.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endDate = voucherRequest.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (endDate.isBefore(startDate)) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Invalid voucher dates", null));
            }
            long daysDifference = endDate.toEpochDay() - startDate.toEpochDay();
            boolean isActive = daysDifference > 0;
            if (voucherRepository.existsByVoucherCode(voucherRequest.getVoucherCode())) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Duplicate voucher code", null));
            }
            double discountPercentage = 0;
            if (voucherRequest.getTotalPrice() >= 5000 && voucherRequest.getTotalPrice() < 10000) {
                discountPercentage = 10;
            } else if (voucherRequest.getTotalPrice() >= 10000) {
                discountPercentage = 20;
            }
            voucher = Voucher.builder()
                    .voucherCode(voucherRequest.getVoucherCode())
                    .discountPercentage(discountPercentage)
                    .startDate(voucherRequest.getStartDate())
                    .endDate(voucherRequest.getEndDate())
                    .active(isActive)
                    .build();
            voucher = voucherRepository.save(voucher);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Create voucher fail", null));
        }
        return ResponseEntity.ok().body(new ResponseObject("Success", "Create voucher success", voucher));
    }


    @Override
    public ResponseEntity<ResponseObject> findAllVoucher() {
        List<Voucher> voucherList = voucherRepository.findAll();
        return ResponseEntity.ok().body(new ResponseObject("Success", "List all succees", voucherList));
    }


    @Override
    public Voucher deleteVoucher(Integer voucherId) {
        String token = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest()
                .getHeader("Authorization")
                .substring(7);
        String userEmail = jwtService.extractUsername(token);
        var requester = userRepository.findUserByEmail(userEmail).orElse(null);
        if (requester == null || !(requester.getRole().equals(Role.staff) || requester.getRole().equals(Role.admin))) {
            return null;
        }
        var voucher = voucherRepository.findById(voucherId).orElse(null);
        assert voucher != null;
        voucherRepository.delete(voucher);
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> searchVoucher(String voucherCode) {
        boolean exists = voucherRepository.existsByVoucherCode(voucherCode);
        Voucher voucher = voucherRepository.findByVoucherCode(voucherCode);
        if (exists) {
            return ResponseEntity.ok().body(new ResponseObject("Success", "Voucher found", voucher));
        } else {
            return ResponseEntity.status(404).body(new ResponseObject("Fail", "Voucher not found", null));
        }
    }


}
