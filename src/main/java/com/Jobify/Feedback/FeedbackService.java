package com.Jobify.Feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;
    public List<Feedback> getAllFeedbacks() {
        List<Feedback> feedbacks = new ArrayList<>();
        feedbackRepository.findAll().forEach(feedbacks::add);
        return feedbacks;
    }
    public Feedback getFeedback(String email) {
        return feedbackRepository.findById(email).get();
    }
    public void addFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    public void updateFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    public void deleteFeedback(String email) {
        feedbackRepository.deleteById(email);
    }
}
