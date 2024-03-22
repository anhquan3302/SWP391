//package com.example.securityl.services.impl;
//
//
//import com.example.securityl.converter.BookingConverter;
//import com.example.securityl.dtos.BookingDto;
//import com.example.securityl.exceptions.DataNotFoundException;
//import com.example.securityl.models.Booking;
//import com.example.securityl.models.Design;
//import com.example.securityl.models.User;
//import com.example.securityl.repositories.BookingRepository;
//import com.example.securityl.repositories.DesignRepository;
//import com.example.securityl.repositories.ProjectBookingRepository;
//import com.example.securityl.repositories.UserRepository;
//import com.example.securityl.services.IBookingService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class BookingService implements IBookingService {
//
//    private final BookingRepository bookingRepository;
//    private final DesignRepository designRepository;
//    private final ProjectBookingRepository projectBookingRepository;
//    private final UserRepository userRepository;
//
//
//    public BookingDto registerBooking(BookingDto bookingDto) throws DataNotFoundException {
//        try {
//            User user = userRepository.findById(bookingDto.getUserId())
//                    .orElseThrow(() ->
//                            new DataNotFoundException(
//                                    "Cannot find user with id: " + bookingDto.getUserId()));
//            Booking booking = BookingConverter.toEntity(bookingDto);
//            booking.setUser(user);
//
//            booking = bookingRepository.save(booking);
//            return BookingConverter.toDTO(booking);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error register booking: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public Design createDesign(Design design) {
//        return designRepository.save(design);
//    }
//
//    @Override
//    public Booking getBookingById(Long bookingId) {
//        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
//        return bookingOptional.orElse(null);
//    }
//
//    @Override
//    public Page<BookingDto> getAllBookingDtos(Pageable pageable) {
//        Page<Booking> bookingsPage = bookingRepository.findAll(pageable);
//        return bookingsPage.map(BookingConverter::toDTO);
//    }
//
//    @Override
//    public Page<BookingDto> getAllBookingDtosByUserId(Pageable pageable, Long userId) {
//        Page<Booking> bookingsPage = bookingRepository.findAllByUserId(pageable, userId);
//        return bookingsPage.map(BookingConverter::toDTO);
//    }
//
//
//}
