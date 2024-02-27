package com.example.securityl.service;

import com.example.securityl.model.Feedback;
import com.example.securityl.request.FeedBackRequest.FeedBackRequest;
import com.example.securityl.response.BlogResponse.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FeedBackService {

    ResponseEntity<ResponseObject> createFeedback(FeedBackRequest feedbackRequest);

    ResponseEntity<ResponseObject> updateFeedback(int feedbackId, FeedBackRequest feedbackRequest);

    ResponseEntity<ResponseObject> deleteFeedback(int feedbackId);

    ResponseEntity<ResponseObject> findFeedbackById(int feedbackId);

    ResponseEntity<ResponseObject> findAllFeedback();
}
