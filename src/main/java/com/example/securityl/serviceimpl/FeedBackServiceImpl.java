package com.example.securityl.serviceimpl;

import com.example.securityl.model.Feedback;
import com.example.securityl.repository.FeedBackRepository;
import com.example.securityl.repository.ProductRepository;
import com.example.securityl.repository.UserRepository;
import com.example.securityl.request.FeedBackRequest.FeedBackRequest;
import com.example.securityl.service.FeedBackService;
import com.example.securityl.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedBackServiceImpl implements FeedBackService {

    private final FeedBackRepository feedbackRepository;
    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    public FeedBackServiceImpl(FeedBackRepository feedbackRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Feedback createFeedback(FeedBackRequest feedBackRequest) {
        Feedback feedback = new Feedback();
        feedback.setContent(feedBackRequest.getContent());
        feedback.setCreatedAt(feedBackRequest.getCreatedAt());

        // Assuming that userRepository and productsRepository are properly implemented
        feedback.setUser(userRepository.findById(feedBackRequest.getUserId()).orElse(null));
        feedback.setProduct(productRepository.findById(feedBackRequest.getProductId()).orElse(null));

        return feedbackRepository.save(feedback);
    }

    @Override
    public Feedback updateFeedback(int id, Feedback feedback) {
        Optional<Feedback> existingFeedbackOptional = feedbackRepository.findById(id);
        if (existingFeedbackOptional.isPresent()) {
            feedback.setFeedbackId(id);
            return feedbackRepository.save(feedback);
        }
        return null; // or throw exception indicating feedback not found
    }

    @Override
    public void deleteFeedback(int id) {
        feedbackRepository.deleteById(id);
    }

    @Override
    public List<Feedback> findAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback findFeedbackById(int id) {
        Optional<Feedback> feedbackOptional = feedbackRepository.findById(id);
        return feedbackOptional.orElse(null); // or throw exception indicating feedback not found
    }

}
