package com.example.securityl.repository;

import com.example.securityl.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedBackRepository extends JpaRepository<Feedback, Integer> {
}
