package com.example.securityl.converter;


import com.example.securityl.dtos.ProjectBookingDto;
import com.example.securityl.exceptions.DataNotFoundException;
import com.example.securityl.models.ProjectBooking;
import com.example.securityl.repositories.BookingRepository;
import com.example.securityl.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectBookingConverter {
  private static UserRepository userRepository;
    @Autowired
    public ProjectBookingConverter(UserRepository userRepository) {
        ProjectBookingConverter.userRepository = userRepository;
    }
    public static ProjectBookingDto toDTO(ProjectBooking projectBooking) {
        ProjectBookingDto.ProjectBookingDtoBuilder builder = ProjectBookingDto.builder()
                .id(projectBooking.getId())
                .projectName(projectBooking.getProjectName())
                .projectType(projectBooking.getProjectType())
                .size(projectBooking.getSize())
                .designStyle(projectBooking.getDesignStyle())
                .colorSchemes(projectBooking.getColorSchemes())
                .intendUse(projectBooking.getIntendUse())
                .occupantsNumber(projectBooking.getOccupantsNumber())
                .timeLine(projectBooking.getTimeLine())
                .projectPrice(projectBooking.getProjectPrice())
                .code(projectBooking.getCode());



        if (projectBooking.getUser() != null) {
            builder.userId(projectBooking.getUser().getId());
        }
        if (projectBooking.getBooking() != null) {
            builder.bookingId(projectBooking.getBooking().getId());
        }

        return builder.build();
    }
    public static ProjectBooking toEntity(ProjectBookingDto projectBookingDto, UserRepository userRepository, BookingRepository bookingRepository) throws DataNotFoundException {
        return ProjectBooking.builder()
                .id(projectBookingDto.getId())
                .projectName(projectBookingDto.getProjectName())
                .projectType(projectBookingDto.getProjectType())
                .size(projectBookingDto.getSize())
                .designStyle(projectBookingDto.getDesignStyle())
                .colorSchemes(projectBookingDto.getColorSchemes())
                .intendUse(projectBookingDto.getIntendUse())
                .occupantsNumber(projectBookingDto.getOccupantsNumber())
                .timeLine(projectBookingDto.getTimeLine())
                .projectPrice(projectBookingDto.getProjectPrice())
                .code(projectBookingDto.getCode())
                .user(userRepository.findById(projectBookingDto.getUserId())
                        .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + projectBookingDto.getUserId())))
                .booking(bookingRepository.findById(projectBookingDto.getBookingId())
                        .orElseThrow(() -> new DataNotFoundException("Cannot find booking with id: " + projectBookingDto.getBookingId())))
                .build();
    }
}
