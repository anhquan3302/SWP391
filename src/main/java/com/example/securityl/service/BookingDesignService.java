package com.example.securityl.service;

import com.example.securityl.dto.request.CreateFormBookingRequest;
import com.example.securityl.dto.request.response.CreateFormBookingResponse;
import org.springframework.http.ResponseEntity;

public interface BookingDesignService {

    ResponseEntity<CreateFormBookingResponse> createBooking(CreateFormBookingRequest createFormBookingRequest);
}
