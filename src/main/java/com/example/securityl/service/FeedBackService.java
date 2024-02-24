package com.example.securityl.service;

import com.example.securityl.model.Feedback;
import com.example.securityl.request.FeedBackRequest.FeedBackRequest;

import java.util.List;

public interface FeedBackService {
    Feedback createFeedback(FeedBackRequest feedback);

    Feedback updateFeedback(int id, Feedback feedback);

    void deleteFeedback(int id);

    List<Feedback> findAllFeedbacks();

    Feedback findFeedbackById(int id);
}
