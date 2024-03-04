package com.example.securityl.serviceimpl;


import com.example.securityl.model.BookingDesign;
import com.example.securityl.model.Designer;
import com.example.securityl.model.User;
import com.example.securityl.repository.BlogRepository;
import com.example.securityl.repository.BookingDesignRepository;
import com.example.securityl.repository.DesinerRepository;
import com.example.securityl.repository.UserRepository;
import com.example.securityl.request.CreateFormBookingRequest;
import com.example.securityl.response.CreateFormBookingResponse;
import com.example.securityl.service.BookingDesignService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service

public class BookingDesignServiceImpl implements BookingDesignService {
    private final BookingDesignRepository bookingDesignRepository;
    private final DesinerRepository desinerRepository;
    private final UserRepository userRepository;

    public BookingDesignServiceImpl(BookingDesignRepository bookingDesignRepository, DesinerRepository desinerRepository, UserRepository userRepository) {
        this.bookingDesignRepository = bookingDesignRepository;
        this.desinerRepository = desinerRepository;

        this.userRepository = userRepository;
    }


    @Override
    public ResponseEntity<CreateFormBookingResponse> createBooking(CreateFormBookingRequest createFormBookingRequest) {
        try {
            if (createFormBookingRequest.getDateTime() == null || createFormBookingRequest.getDesignDescription() == null || createFormBookingRequest.getDesignDescription().isEmpty()) {
                return ResponseEntity.badRequest().body(new CreateFormBookingResponse("Fail", "DateTime and DesignDescription are required", null));
            }

            // Assuming designerId is provided in the request
            Designer designer = desinerRepository.findById(createFormBookingRequest.getDesignerId()).orElse(null);
            if (designer == null) {
                return ResponseEntity.badRequest().body(new CreateFormBookingResponse("Fail", "Invalid designerId", null));
            }

            // Assuming userId is provided in the request
            User user = userRepository.findById(createFormBookingRequest.getUserId()).orElse(null);
            if (user == null) {
                return ResponseEntity.badRequest().body(new CreateFormBookingResponse("Fail", "Invalid userId", null));
            }

            BookingDesign booking = new BookingDesign();
            booking.setDateTime(createFormBookingRequest.getDateTime());
            booking.setDesign_description(createFormBookingRequest.getDesignDescription());
            booking.setMeetingDate(createFormBookingRequest.getMeetingDate());
            booking.setMeetingTime(createFormBookingRequest.getMeetingTime());

            // Set other fields similarly
            booking.setDesigner(designer);
            booking.setUser(user);

            BookingDesign savedBooking = bookingDesignRepository.save(booking);

            return ResponseEntity.ok(new CreateFormBookingResponse("Success", "Booking created successfully", savedBooking));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new CreateFormBookingResponse("Fail", "Internal Server Error", null));
        }
    }
    }

