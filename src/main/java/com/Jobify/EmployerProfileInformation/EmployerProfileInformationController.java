package com.Jobify.EmployerProfileInformation;

import com.Jobify.Applications.Applications;
import com.Jobify.Applications.ApplicationsService;
import com.Jobify.Feedback.FeedbackService;
import com.Jobify.JobPostings.JobPostings;
import com.Jobify.JobPostings.JobPostingsService;
import com.Jobify.loginInformation.LoginInformationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class EmployerProfileInformationController {

    @Autowired
    private EmployerProfileInformationService employerProfileInformationService;
    @Autowired
    private LoginInformationService loginInformationService;
    @Autowired
    FeedbackService feedbackService;
    @Autowired
    JobPostingsService jobPostingsService;
    @Autowired
    ApplicationsService applicationsService;

    //Not currently using it
    @RequestMapping("/admin/view-employers")
    public List<EmployerProfileInformation> getAllEmployers() {
        return employerProfileInformationService.getAllEmployers();
    }

    @RequestMapping("/employer/profile")
    public String getEmployer(ModelMap modelMap, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        EmployerProfileInformation employerProfileInformation = employerProfileInformationService.getEmployer(email);
        modelMap.addAttribute("employerInformation", employerProfileInformation);
        modelMap.addAttribute("email", email);
        return "/Employer/EmployerHomePage";
    }

    @RequestMapping("/admin/all-users/employer/{employerEmail}")
    public String getEmployerForAdmin(@PathVariable String employerEmail, ModelMap modelMap, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        EmployerProfileInformation employer = employerProfileInformationService.getEmployer(employerEmail);
        modelMap.addAttribute("employerInformation", employer);
        return "/Admin/ViewEmployerUserProfile";
    }

    @GetMapping("/signUp/employer")
    public String employerCreationForm(HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        return "/Creating account/CreatingEmployerProfile";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signUp/employer")
    public String addEmployer(EmployerProfileInformation employerProfileInformation, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        employerProfileInformation.setEmail(email);
        employerProfileInformationService.addEmployer(employerProfileInformation);
        return "redirect:/employer";
    }

    @RequestMapping("/employer/profile/edit")
    public String updateEmployerForm(HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        EmployerProfileInformation employer = employerProfileInformationService.getEmployer(email);
        modelMap.addAttribute("employerInformation", employer);
        return "/Employer/EditingEmployerProfile";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/employer/profile/edit")
    public String updateEmployer(EmployerProfileInformation employerProfileInformation, ModelMap modelMap, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        employerProfileInformation.setEmail(email);
        modelMap.addAttribute("email", email);
        employerProfileInformationService.updateEmployer(employerProfileInformation);
        return "redirect:/employer/profile";
    }

    @RequestMapping("/admin/all-users/employer/{employerEmail}/edit")
    public String updateEmployerFormFromAdmin(HttpServletRequest request, ModelMap modelMap, @PathVariable String employerEmail) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        EmployerProfileInformation employer = employerProfileInformationService.getEmployer(employerEmail);
        modelMap.addAttribute("employerInformation", employer);
        return "/Admin/EditingEmployerProfileAdmin";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/all-users/employer/{employerEmail}/edit")
    public String updateEmployerFromAdmin(@PathVariable String employerEmail, EmployerProfileInformation employerProfileInformation, ModelMap modelMap, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        employerProfileInformation.setEmail(employerEmail);
        modelMap.addAttribute("email", email);
        employerProfileInformationService.updateEmployer(employerProfileInformation);
        return "redirect:/admin/all-users/employer/" + employerEmail;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/all-users/employer/{employerEmail}/delete")
    public String deleteEmployerFromAdmin(@PathVariable String employerEmail, HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        List<JobPostings> jobPostings = jobPostingsService.getAllJobPostingsByEmployer(employerEmail);
        for (JobPostings jobPosting : jobPostings) {
            List<Applications> applications = applicationsService.getAllApplicationsByJobPostingId(jobPosting.getId());
            for (Applications application : applications) {
                applicationsService.deleteApplication(application.getId());
            }
            jobPostingsService.deleteJobPosting(jobPosting.getId());
        }
        feedbackService.deleteFeedback(employerEmail);
        employerProfileInformationService.deleteEmployer(employerEmail);
        loginInformationService.deleteUser(employerEmail);
        modelMap.addAttribute("email", email);
        return "redirect:/admin/all-users";
    }
}