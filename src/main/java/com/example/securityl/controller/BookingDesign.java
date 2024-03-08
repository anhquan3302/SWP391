package com.example.securityl.controller;

import com.example.securityl.dto.request.CreateFormBookingRequest;
import com.example.securityl.dto.request.response.CreateFormBookingResponse;
import com.example.securityl.service.BookingDesignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/Booking")
public class BookingDesign {
    private final BookingDesignService bookingDesignService;

    @PostMapping("/Createbookings")
    public ResponseEntity<CreateFormBookingResponse> createBooking(@RequestBody CreateFormBookingRequest createFormBookingRequest) {
        return bookingDesignService.createBooking(createFormBookingRequest);
    }


}
