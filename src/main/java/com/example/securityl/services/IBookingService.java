//package com.example.securityl.services;
//
//
//import com.example.securityl.dtos.BookingDto;
//import com.example.securityl.exceptions.DataNotFoundException;
//import com.example.securityl.models.Booking;
//import com.example.securityl.models.Design;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//public interface IBookingService {
//
//    Design createDesign(Design design);
//
//    Booking getBookingById(Long bookingId);
//
//    BookingDto registerBooking(BookingDto bookingDto) throws DataNotFoundException;
//
//    Page<BookingDto> getAllBookingDtos(Pageable pageable);
//
//    Page<BookingDto> getAllBookingDtosByUserId(Pageable pageable, Long userId);
//}
