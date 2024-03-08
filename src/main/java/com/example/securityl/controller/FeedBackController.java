package com.example.securityl.controller;

import com.example.securityl.model.Feedback;
import com.example.securityl.request.FeedBackRequest.FeedBackRequest;
import com.example.securityl.response.BlogResponse.ResponseObject;
import com.example.securityl.service.FeedBackService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/managementFeedBack")
public class FeedBackController {


    private final FeedBackService feedbackService;


    @PostMapping("/createFeedback")
    public ResponseEntity<ResponseObject> createFeedback(@RequestBody FeedBackRequest feedbackRequest) {
        return feedbackService.createFeedback(feedbackRequest);
    }

    @PutMapping("/updateFeedback/{feedbackId}")
    public ResponseEntity<ResponseObject> updateFeedback(
            @PathVariable int feedbackId,
            @RequestBody FeedBackRequest feedbackRequest) {
        return feedbackService.updateFeedback(feedbackId, feedbackRequest);
    }



    @GetMapping("/getAllFeedback")
    public ResponseEntity<ResponseObject> getAllFeedback() {
        return feedbackService.findAllFeedback();
    }
}

