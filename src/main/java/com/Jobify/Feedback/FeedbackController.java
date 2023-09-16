package com.Jobify.Feedback;

import com.Jobify.loginInformation.LoginInformation;
import com.Jobify.loginInformation.LoginInformationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final LoginInformationService loginInformationService;

    public FeedbackController(FeedbackService feedbackService, LoginInformationService loginInformationService) {
        this.feedbackService = feedbackService;
        this.loginInformationService = loginInformationService;
    }

    @RequestMapping("/admin/feedbacks")
    public String getAllFeedbacks(ModelMap modelMap) {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        modelMap.addAttribute("feedbacks", feedbacks);
        return "/Admin/ViewUserFeedback";
    }

    @GetMapping("/{accountType}/feedback")
    public String reviewForm(HttpServletRequest request, ModelMap modelMap, @PathVariable String accountType) {
        String email = (String) request.getSession().getAttribute("email");
        LoginInformation loginInformation = loginInformationService.getUser(email);
        modelMap.addAttribute("loginInformation", loginInformation);
        modelMap.addAttribute("accountType", accountType);
        Feedback feedback = null;
        try {
            feedback = feedbackService.getFeedback(email);
        } catch (NoSuchElementException ignored) {
        }
        modelMap.addAttribute("feedback", feedback);
        return "AboutPage";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{userType}/feedback")
    public String addFeedback(@PathVariable String userType, HttpServletRequest request, @RequestParam("rate") String rate, @RequestParam("subject") String subject) {
        String email = (String) request.getSession().getAttribute("email");
        Feedback feedback = new Feedback(email, subject, Integer.parseInt(rate));
        feedbackService.addFeedback(feedback);
        return "redirect:/" + userType;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{userType}/feedback/edit")
    public String editFeedback(@PathVariable String userType, HttpServletRequest request, @RequestParam("rate") String rate, @RequestParam("subject") String subject) {
        String email = (String) request.getSession().getAttribute("email");
        Feedback feedback = new Feedback(email, subject, Integer.parseInt(rate));
        feedbackService.updateFeedback(feedback);
        return "redirect:/" + userType + "/feedback";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userType}/feedback/delete")
    public String deleteFeedback(HttpServletRequest request, @PathVariable String userType) {
        String email = (String) request.getSession().getAttribute("email");
        feedbackService.deleteFeedback(email);
        return "redirect:/" + userType;
    }
}