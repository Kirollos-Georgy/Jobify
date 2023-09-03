package com.Jobify.Feedback;

import com.Jobify.loginInformation.LoginInformation;
import com.Jobify.loginInformation.LoginInformationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private LoginInformationService loginInformationService;

    @RequestMapping("/{email}/admin/feedbacks")
    public String getAllFeedbacks(HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        modelMap.addAttribute("feedbacks", feedbacks);
        modelMap.addAttribute("email", email);
        return "ViewUserFeedback";
    }

    @GetMapping("/{accountType}/feedback")
    public String reviewForm(HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        LoginInformation loginInformation = loginInformationService.getUser(email);
        modelMap.addAttribute("email", email);
        modelMap.addAttribute("loginInformation", loginInformation);
        return "AboutPage";
    }

    /*@RequestMapping("/{email}/student/feedback")
    public Feedback getFeedbackStudent(@PathVariable String email) {
        return feedbackService.getFeedback(email);
    }

    @RequestMapping("/{email}/employer/feedback")
    public Feedback getFeedbackEmployer(@PathVariable String email) {
        return feedbackService.getFeedback(email);
    }*/

    @RequestMapping(method = RequestMethod.POST, value = "/{email}/{userType}/feedback")
    public String addFeedback(@PathVariable String userType, HttpServletRequest request, ModelMap modelMap, @RequestParam("rate") String rate, @RequestParam("subject") String subject) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        Feedback feedback = new Feedback(email, subject, Integer.parseInt(rate));
        feedbackService.addFeedback(feedback);
        return "redirect:/" + email + "/" + userType;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{email}/student/feedback/edit")
    public void updateFeedbackStudent(@RequestBody Feedback Feedback) {
        feedbackService.updateFeedback(Feedback);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{email}/employer/feedback/edit")
    public void updateFeedbackEmployer(@RequestBody Feedback Feedback) {
        feedbackService.updateFeedback(Feedback);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{email}/student/feedback/delete")
    public void deleteFeedbackStudent(@PathVariable String email) {
        feedbackService.deleteFeedback(email);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{email}/employer/feedback/delete")
    public void deleteFeedbackEmployer(@PathVariable String email) {
        feedbackService.deleteFeedback(email);
    }
}
