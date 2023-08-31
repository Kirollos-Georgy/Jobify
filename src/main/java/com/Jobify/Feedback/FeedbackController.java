package com.Jobify.Feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping("/jobify/{email}/admin/feedbacks")
    public List<Feedback> getAllFeedbacks() {
        return feedbackService.getAllFeedbacks();
    }

    @RequestMapping("/jobify/{email}/student/feedback")
    public Feedback getFeedbackStudent(@PathVariable String email) {
        return feedbackService.getFeedback(email);
    }

    @RequestMapping("/jobify/{email}/employer/feedback")
    public Feedback getFeedbackEmployer(@PathVariable String email) {
        return feedbackService.getFeedback(email);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/jobify/{email}/student/feedback")
    public void addFeedbackStudent(@RequestBody Feedback Feedback) {
        feedbackService.addFeedback(Feedback);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/jobify/{email}/employer/feedback")
    public void addFeedbackEmployer(@RequestBody Feedback Feedback) {
        feedbackService.addFeedback(Feedback);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/jobify/{email}/student/feedback/edit")
    public void updateFeedbackStudent(@RequestBody Feedback Feedback) {
        feedbackService.updateFeedback(Feedback);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/jobify/{email}/employer/feedback/edit")
    public void updateFeedbackEmployer(@RequestBody Feedback Feedback) {
        feedbackService.updateFeedback(Feedback);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/jobify/{email}/student/feedback/delete")
    public void deleteFeedbackStudent(@PathVariable String email) {
        feedbackService.deleteFeedback(email);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/jobify/{email}/employer/feedback/delete")
    public void deleteFeedbackEmployer(@PathVariable String email) {
        feedbackService.deleteFeedback(email);
    }
}
