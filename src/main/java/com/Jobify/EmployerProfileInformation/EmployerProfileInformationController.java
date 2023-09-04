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
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/{email}/admin/view-employers")
    public List<EmployerProfileInformation> getAllEmployers() {
        return employerProfileInformationService.getAllEmployers();
    }

    @RequestMapping("/{email}/employer/profile")
    public String getEmployer(@PathVariable String email, ModelMap modelMap, HttpServletRequest request) {
        EmployerProfileInformation employerProfileInformation = employerProfileInformationService.getEmployer(email);
        modelMap.addAttribute("employerInformation", employerProfileInformation);
        String email1 = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email1);
        return "EmployerHomePage";
    }

    @RequestMapping("/{email}/admin/all-users/employer/{employerEmail}")
    public String getEmployerForAdmin(@PathVariable String employerEmail, ModelMap modelMap, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        EmployerProfileInformation employer = employerProfileInformationService.getEmployer(employerEmail);
        modelMap.addAttribute("employerInformation", employer);
        return "ViewEmployerUserProfile";
    }

    /*@RequestMapping(method = RequestMethod.POST, value = "/signUp/employer")
    public String addEmployer(@RequestBody EmployerProfileInformation employerProfileInformation) {
        employerProfileInformationService.addEmployer(employerProfileInformation);
        return "redirect:/" + employerProfileInformation.getEmail() + "/employer";
    }*/

    //Works
    @GetMapping("/signUp/employer")
    public String employerCreationForm(HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        return "CreatingEmployerProfile";
    }

    //Works
    @RequestMapping(method = RequestMethod.POST, value = "/signUp/{email}/employer")
    public String addEmployer(EmployerProfileInformation employerProfileInformation, @PathVariable String email) {
        employerProfileInformation.setEmail(email);
        employerProfileInformationService.addEmployer(employerProfileInformation);
        return "redirect:/" + email + "/employer";
    }

    @RequestMapping("/employer/profile/edit")
    public String updateEmployerForm(HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        EmployerProfileInformation employer = employerProfileInformationService.getEmployer(email);
        modelMap.addAttribute("employerInformation", employer);
        return "EditingEmployerProfile";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{email}/employer/profile/edit")
    public String updateEmployer(EmployerProfileInformation employerProfileInformation, @PathVariable String email, ModelMap modelMap, HttpServletRequest request) {
        employerProfileInformation.setEmail(email);
        String email1 = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email1);
        employerProfileInformationService.updateEmployer(employerProfileInformation);
        return "redirect:/" + email + "/employer/profile";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{email}/admin/view-employers/{employerEmail}/edit")
    public void updateEmployerFromAdmin(@RequestBody EmployerProfileInformation employerProfileInformation) {
        employerProfileInformationService.updateEmployer(employerProfileInformation);
    }

    /*@RequestMapping(method = RequestMethod.DELETE, value = "/{email}/employer/profile/delete")
    public void deleteEmployer(@PathVariable String email) {
        employerProfileInformationService.deleteEmployer(email);
    }*/

    @RequestMapping(method = RequestMethod.POST, value = "/{email}/admin/all-users/employer/{employerEmail}/delete")
    public String deleteEmployerFromAdmin(@PathVariable String employerEmail, HttpServletRequest request, ModelMap modelMap ) {
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
        return "redirect:/" + email + "/admin/all-users";
    }
}
