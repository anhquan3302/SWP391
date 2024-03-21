package com.example.securityl.converter;

import com.example.securityl.model.Feedback;
import com.example.securityl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class FeedbackConverter {
    private static UserRepository userRepository;  // Add UserRepository


    public FeedbackConverter(UserRepository userRepository) {
        FeedbackConverter.userRepository = userRepository;
    }

    public static Feedback toDto(Feedback feedback) {
        Feedback.FeedbackBuilder builder = Feedback.builder()
                .feedbackId(feedback.getFeedbackId())
                .content(feedback.getContent())
                .createdAt(feedback.getCreatedAt())
                .product(feedback.getProduct() != null ? feedback.getProduct() : null);

        // Add user mapping
        if (feedback.getUser() != null) {
            builder.user(feedback.getUser());
        }

        return builder.build();
    }

    public static Feedback toEntity(Feedback feedbackDto) throws Exception {
        return Feedback.builder()
                .feedbackId(feedbackDto.getFeedbackId())
                .content(feedbackDto.getContent())
                .createdAt(feedbackDto.getCreatedAt())

                .build();
    }




}
