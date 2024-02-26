package com.example.securityl.controller;

import com.example.securityl.model.Feedback;
import com.example.securityl.request.FeedBackRequest.FeedBackRequest;
import com.example.securityl.service.FeedBackService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER','STAFF')")
@RequestMapping("/api/v1/managementFeedBack")
public class FeedBackController {



    private final FeedBackService feedbackService;

    @PostMapping("/CreateFeedBack")
    public Feedback createFeedback( @RequestBody FeedBackRequest feedBackRequest) {
        return feedbackService.createFeedback(feedBackRequest);
    }

    @PutMapping("/updateFeedBack/{id}")
    public Feedback updateFeedback(@PathVariable int id, @RequestBody Feedback feedback) {
        return feedbackService.updateFeedback(id, feedback);
    }

    @DeleteMapping("/deleteFeedBack/{id}")
    public void deleteFeedback(@PathVariable int id) {
        feedbackService.deleteFeedback(id);
    }

    @GetMapping("/FindAllFeedBack")
    public List<Feedback> findAllFeedbacks() {
        return feedbackService.findAllFeedbacks();
    }

    @GetMapping("/findFeedBackById/{id}")
    public Feedback findFeedbackById(@PathVariable int id) {
        return feedbackService.findFeedbackById(id);
    }
}