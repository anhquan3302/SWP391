package com.example.securityl.serviceimpl;

import com.example.securityl.model.CartItem;
import com.example.securityl.model.Enum.Role;
import com.example.securityl.model.Voucher;
import com.example.securityl.repository.UserRepository;
import com.example.securityl.repository.VoucherRepository;
import com.example.securityl.dto.request.VoucherRequest.VoucherRequest;
import com.example.securityl.dto.request.response.ObjectResponse.ResponseObject;
import com.example.securityl.service.JwtService;
import com.example.securityl.service.ShoppingCartService;
import com.example.securityl.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final ShoppingCartService shoppingCartService;

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

    @Override
    public ResponseEntity<ResponseObject> applyVoucher(String voucherCode) {
        List<CartItem> cartItems = (List<CartItem>) shoppingCartService.getAllCart();
        if (cartItems == null || cartItems.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("Fail", "Cart is empty", null));
        }
        double totalPrice = shoppingCartService.getTotal();
        Voucher voucher = voucherRepository.findByVoucherCode(voucherCode);
        if (voucher != null) {
            if (voucher.isActive()) {
                Date currentDate = new Date();
                if (currentDate.after(voucher.getStartDate()) && currentDate.before(voucher.getEndDate())) {
                    if (isTotalPriceQualified(totalPrice, voucher)) {
                        double discountedPrice = calculateDiscount(totalPrice, voucher);
                        updateTotalPriceInCart(cartItems, discountedPrice);

                        return ResponseEntity.ok()
                                .body(new ResponseObject("Success", "Voucher applied successfully", discountedPrice));
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ResponseObject("Fail", "Total price does not meet voucher conditions", null));
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ResponseObject("Fail", "Voucher inactive or expired", null));
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseObject("Fail", "Invalid voucher code", null));
            }
        }
        return null;
    }


    @Override
    public ResponseEntity<ResponseObject> updateVoucher(Integer voucherId, VoucherRequest voucherRequest) {
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
            Optional<Voucher> optionalVoucher = voucherRepository.findById(voucherId);
            if (!optionalVoucher.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            Voucher voucher = optionalVoucher.get();
            voucher.setVoucherCode(voucherRequest.getVoucherCode());
            voucher.setStartDate(voucherRequest.getStartDate());
            voucher.setEndDate(voucherRequest.getEndDate());
            voucher.setActive(voucherRequest.isActive());
            voucherRepository.save(voucher);

            return ResponseEntity.ok().body(new ResponseObject("Success", "Voucher updated successfully", voucher));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("Fail", "Failed to update voucher", null));
        }
    }



    private boolean isTotalPriceQualified(double totalPrice, Voucher voucher) {
        if (totalPrice >= 100000 && voucher.getDiscountPercentage() == 20) {
            return true;
        } else if (totalPrice >= 50000 && voucher.getDiscountPercentage() == 10) {
            return true;
        }
        return false;
    }


    private void updateTotalPriceInCart(List<CartItem> cartItems, double discountedPrice) {
        double totalOriginalPrice = shoppingCartService.getTotal();
        for (CartItem cartItem : cartItems) {
            double originalPrice = cartItem.getPrice();
            int quantity = cartItem.getQuantity();
            double priceAfterDiscount = (originalPrice / quantity) * (1 - (discountedPrice / totalOriginalPrice));
            cartItem.setPrice(priceAfterDiscount);
        }
    }



    private double calculateDiscount(double totalPrice, Voucher voucher) {
        double discountPercentage = voucher.getDiscountPercentage();
        if (totalPrice >= 100000) {
            discountPercentage = 20;
        } else if (totalPrice >= 50000) {
            discountPercentage = 10;
        } else {
            discountPercentage = 0;
        }
        double discountAmount = (totalPrice * discountPercentage) / 100;
        return totalPrice - discountAmount;
    }






}
