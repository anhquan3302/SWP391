package com.example.securityl.serviceimpl;

import com.example.securityl.model.Feedback;
import com.example.securityl.model.User;
import com.example.securityl.repository.FeedBackRepository;
import com.example.securityl.repository.ProductRepository;
import com.example.securityl.repository.UserRepository;
import com.example.securityl.dto.request.FeedBackRequest.FeedBackRequest;
import com.example.securityl.dto.request.response.BlogResponse.ResponseObject;
import com.example.securityl.service.FeedBackService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public ResponseEntity<ResponseObject> createFeedback(FeedBackRequest feedbackRequest) {
        try {
            // Kiểm tra nội dung không được trống
            if (feedbackRequest.getContent() == null || feedbackRequest.getContent().isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Content is required", null));
            }

            // Kiểm tra nội dung có ít nhất 5 ký tự
            if (feedbackRequest.getContent().length() < 5) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Content must have at least 5 characters", null));
            }

            // Kiểm tra nội dung không được vượt quá 250 ký tự
            if (feedbackRequest.getContent().length() > 250) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Content must have at most 250 characters", null));
            }

            // Kiểm tra nội dung không chứa quá 4 ký tự liền kề trùng lặp
            if (containsDuplicateCharacters(feedbackRequest.getContent(), 4)) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Content cannot contain more than 4 consecutive duplicate characters", null));
            }

            Feedback feedback = new Feedback();
            feedback.setContent(feedbackRequest.getContent());
            feedback.setCreatedAt(new Date());

            User user = userRepository.findById(feedbackRequest.getUserId()).orElse(null);
            if (user == null) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "User not found", null));
            }
            feedback.setUser(user);

            Feedback savedFeedback = feedbackRepository.save(feedback);

            return ResponseEntity.ok(new ResponseObject("Success", "Feedback created successfully", savedFeedback));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("Fail", "Internal Server Error", null));
        }
    }

    // Phương thức kiểm tra nội dung có chứa quá số lượng ký tự liền kề trùng lặp cho trước không
    private boolean containsDuplicateCharacters(String content, int maxConsecutiveDuplicates) {
        for (int i = 0; i < content.length() - maxConsecutiveDuplicates; i++) {
            int count = 1;
            for (int j = i + 1; j < i + maxConsecutiveDuplicates; j++) {
                if (content.charAt(i) == content.charAt(j)) {
                    count++;
                } else {
                    break;
                }
            }
            if (count >= maxConsecutiveDuplicates) {
                return true;
            }
        }
        return false;
    }


    @Override
    public ResponseEntity<ResponseObject> updateFeedback(int feedbackId, FeedBackRequest feedbackRequest) {
        try {
            // Kiểm tra xem phản hồi có tồn tại không
            Optional<Feedback> existingFeedbackOptional = feedbackRepository.findById(feedbackId);
            if (existingFeedbackOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Feedback not found", null));
            }

            Feedback existingFeedback = existingFeedbackOptional.get();

            // Cập nhật nội dung phản hồi nếu được cung cấp
            if (feedbackRequest.getContent() != null && !feedbackRequest.getContent().isEmpty()) {
                existingFeedback.setContent(feedbackRequest.getContent());
            }

            // Lưu lại thay đổi
            Feedback updatedFeedback = feedbackRepository.save(existingFeedback);

            return ResponseEntity.ok(new ResponseObject("Success", "Feedback updated successfully", updatedFeedback));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> deleteFeedback(int feedbackId) {
        try {
            // Kiểm tra xem phản hồi có tồn tại không
            Optional<Feedback> existingFeedbackOptional = feedbackRepository.findById(feedbackId);
            if (existingFeedbackOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Feedback not found", null));
            }

            Feedback existingFeedback = existingFeedbackOptional.get();

            // Xóa phản hồi
            feedbackRepository.delete(existingFeedback);

            return ResponseEntity.ok(new ResponseObject("Success", "Feedback deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> findFeedbackById(int feedbackId) {
        try {
            // Tìm kiếm phản hồi theo ID
            Optional<Feedback> feedbackOptional = feedbackRepository.findById(feedbackId);
            if (feedbackOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseObject("Fail", "Feedback not found", null));
            }

            Feedback feedback = feedbackOptional.get();
            return ResponseEntity.ok(new ResponseObject("Success", "Feedback found", feedback));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> findAllFeedback() {
        try {
            // Lấy tất cả các phản hồi từ cơ sở dữ liệu
            List<Feedback> allFeedback = feedbackRepository.findAll();
            return ResponseEntity.ok(new ResponseObject("Success", "All feedbacks", allFeedback));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("Fail", "Internal Server Error", null));
        }
    }
}
