package com.example.securityl.service;

import com.example.securityl.dto.request.FeedBackRequest.FeedBackRequest;
import com.example.securityl.dto.request.response.BlogResponse.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface FeedBackService {

    ResponseEntity<ResponseObject> createFeedback(FeedBackRequest feedbackRequest);

    ResponseEntity<ResponseObject> updateFeedback(int feedbackId, FeedBackRequest feedbackRequest);

    ResponseEntity<ResponseObject> deleteFeedback(int feedbackId);

    ResponseEntity<ResponseObject> findFeedbackById(int feedbackId);

    ResponseEntity<ResponseObject> findAllFeedback();
}
