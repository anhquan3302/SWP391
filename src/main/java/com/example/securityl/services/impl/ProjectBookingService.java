package com.example.securityl.services.impl;


import com.example.securityl.converter.ProjectBookingConverter;
import com.example.securityl.dtos.ProjectBookingDto;
import com.example.securityl.exceptions.DataNotFoundException;
import com.example.securityl.models.Booking;
import com.example.securityl.models.ProjectBooking;
import com.example.securityl.models.User;
import com.example.securityl.repositories.BookingRepository;
import com.example.securityl.repositories.ProjectBookingRepository;
import com.example.securityl.repositories.UserRepository;
import com.example.securityl.services.IProjectBookingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectBookingService implements IProjectBookingService {
    private final ProjectBookingRepository projectBookingRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    @Override
    public ProjectBookingDto createProjectBooking(ProjectBookingDto projectBookingDto) {
        try {
            User user = userRepository.findById(projectBookingDto.getUserId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find user with id: " + projectBookingDto.getUserId()));

            // Lấy bookingId từ projectBookingDto
            Long bookingId = projectBookingDto.getBookingId();
            if (bookingId == null) {
                throw new IllegalArgumentException("Booking ID is required.");
            }

            // Không cần truy vấn bookingRepository, sử dụng trực tiếp bookingId để thiết lập booking
            Booking booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find booking with id: " + bookingId));

            ProjectBooking projectBooking = ProjectBookingConverter.toEntity(projectBookingDto, userRepository, bookingRepository);
            projectBooking.setUser(user);
            projectBooking.setBooking(booking);

            String code = createCodeForProjectBooking();
            projectBooking.setCode(code);

            projectBooking = projectBookingRepository.save(projectBooking);
            return ProjectBookingConverter.toDTO(projectBooking);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error registering project booking: " + e.getMessage());
        }
    }

    private String createCodeForProjectBooking() {
        // Lấy tổng số lượng ProjectBooking hiện có và cộng thêm 1 để tạo mã tiếp theo
        long count = projectBookingRepository.count();
        return "Codepb-" + (count + 1);
    }

   /* @Override
    public ProjectBooking updateProjectBooking(ProjectBooking projectBooking) {
        // Kiểm tra xem có tồn tại ProjectBooking với ID đã cho hay không
        Optional<ProjectBooking> existingProjectBooking = projectBookingRepository.findById(projectBooking.getId());
        if (existingProjectBooking.isPresent()) {
            // Nếu tồn tại, cập nhật các trường của đối tượng hiện có bằng dữ liệu mới
            ProjectBooking updatedProjectBooking = existingProjectBooking.get();

            // Ví dụ cập nhật một số trường, bạn có thể thêm các trường khác tương tự
            updatedProjectBooking.setProjectName(projectBooking.getProjectName());
            updatedProjectBooking.setProjectType(projectBooking.getProjectType());
            updatedProjectBooking.setSize(projectBooking.getSize());
            updatedProjectBooking.setDesignStyle(projectBooking.getDesignStyle());
            updatedProjectBooking.setColorSchemes(projectBooking.getColorSchemes());
            updatedProjectBooking.setIntendUse(projectBooking.getIntendUse());
            updatedProjectBooking.setOccupantsNumber(projectBooking.getOccupantsNumber());
            updatedProjectBooking.setTimeLine(projectBooking.getTimeLine());
            updatedProjectBooking.setProjectPrice(projectBooking.getProjectPrice());
            // Đảm bảo cập nhật tất cả các trường cần thiết theo mô hình của bạn

            // Lưu lại đối tượng đã cập nhật
            return projectBookingRepository.save(updatedProjectBooking);
        } else {

            throw new EntityNotFoundException("ProjectBooking with ID " + projectBooking.getId() + " not found.");
        }
    }*/ public ProjectBooking updateProjectBooking(Long id, ProjectBookingDto projectBookingDto) throws DataNotFoundException {
       // Kiểm tra xem có tồn tại ProjectBooking với ID đã cho hay không
       ProjectBooking existingProjectBooking = projectBookingRepository.findById(id)
               .orElseThrow(() -> new DataNotFoundException("ProjectBooking with id " + id + " not found"));

       // Cập nhật thông tin của ProjectBooking với dữ liệu từ projectBookingDto
       existingProjectBooking.setProjectName(projectBookingDto.getProjectName());
       existingProjectBooking.setProjectType(projectBookingDto.getProjectType());
       existingProjectBooking.setSize(projectBookingDto.getSize());
       existingProjectBooking.setDesignStyle(projectBookingDto.getDesignStyle());
       existingProjectBooking.setColorSchemes(projectBookingDto.getColorSchemes());
       existingProjectBooking.setIntendUse(projectBookingDto.getIntendUse());
       existingProjectBooking.setOccupantsNumber(projectBookingDto.getOccupantsNumber());
       existingProjectBooking.setTimeLine(projectBookingDto.getTimeLine());
       existingProjectBooking.setProjectPrice(projectBookingDto.getProjectPrice());




       // Lưu ProjectBooking đã cập nhật vào cơ sở dữ liệu
       return projectBookingRepository.save(existingProjectBooking);
   }


//    @Override
//    public ProjectBooking getProjectBookingById(Long projectBookingId) {
//
//        Optional<ProjectBooking> bookingOptional = projectBookingRepository.findById(projectBookingId);
//        return bookingOptional.orElse(null);
//    }
    public List<ProjectBooking> getProjectBookingsByUserId(Long userId) {
        return projectBookingRepository.findByUserId(userId);
    }
    public ProjectBookingDto getProjectBookingByCode(String code) throws DataNotFoundException {
        ProjectBooking projectBooking = projectBookingRepository.findByCode(code);
        if (projectBooking == null) {
            throw new DataNotFoundException("ProjectBooking with code " + code + " not found");
        }
        return ProjectBookingConverter.toDTO(projectBooking);
    }

    @Override
    public ProjectBookingDto getProjectBookingByBookingId(String BookingId) {
        return null;
    }

    @Override
    public List<ProjectBookingDto> getAllProjectBookings() {
        List<ProjectBooking> projectBookings = projectBookingRepository.findAll();
        return projectBookings.stream()
                .map(ProjectBookingConverter::toDTO)
                .collect(Collectors.toList());
    }
    public ProjectBookingDto getProjectBookingByBookingId(Long bookingId) throws DataNotFoundException {
        ProjectBooking projectBooking = projectBookingRepository.findByBookingId(bookingId);
        if (projectBooking == null) {
            throw new DataNotFoundException("ProjectBooking with bookingId " + bookingId + " not found");
        }
        return ProjectBookingConverter.toDTO(projectBooking);
    }
        public ProjectBookingDto getProjectBookingById(Long projectBookingId) {
            ProjectBooking projectBooking = projectBookingRepository.findById(projectBookingId)
                    .orElseThrow(() -> new EntityNotFoundException("ProjectBooking with id: " + projectBookingId + " not found"));
            return ProjectBookingConverter.toDTO(projectBooking);
        }


}
