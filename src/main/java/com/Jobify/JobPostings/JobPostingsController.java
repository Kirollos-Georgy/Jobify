package com.Jobify.JobPostings;

import com.Jobify.Applications.Applications;
import com.Jobify.Applications.ApplicationsService;
import com.Jobify.EmployerProfileInformation.EmployerProfileInformation;
import com.Jobify.EmployerProfileInformation.EmployerProfileInformationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RestController
public class JobPostingsController {

    @Autowired
    private JobPostingsService jobPostingsService;
    @Autowired
    private EmployerProfileInformationService employerProfileInformationService;
    @Autowired
    private ApplicationsService applicationsService;

    //Works - not tested with multiple job postings
    @RequestMapping("/{email}/student")
    public String getAllJobPostingsForStudent(ModelMap modelMap, HttpServletRequest request) {
        List<JobPostings> jobPostings = jobPostingsService.getAllJobPostings();
        modelMap.addAttribute("jobPostings", jobPostings);
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        return "/Student/viewJobPostings";
    }


    @RequestMapping("/{email}/admin/job-postings")
    public String getAllJobPostingsForAdmin(ModelMap modelMap, HttpServletRequest request) {
        List<JobPostings> jobPostings = jobPostingsService.getAllJobPostings();
        modelMap.addAttribute("jobPostings", jobPostings);
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        return "/Admin/viewJobPostingsAdmin";
    }

    //Works - not tested with multiple job postings
    @RequestMapping("/{email}/employer")
    public String getAllJobPostingsByEmployer(@PathVariable String email, ModelMap modelMap) {
        List<JobPostings> jobPostings = jobPostingsService.getAllJobPostingsByEmployer(email);
        modelMap.addAttribute("jobPostings", jobPostings);
        return "/Employer/viewCreatedJobPostings";
    }
    @RequestMapping("/{email}/employer/interview")
    public String getAllJobPostingsByEmployerInterview(@PathVariable String email, ModelMap modelMap) {
        List<JobPostings> jobPostings = jobPostingsService.getAllJobPostingsByEmployer(email);
        modelMap.addAttribute("jobPostings", jobPostings);
        return "/Employer/viewCreatedJobPostingsInterview";
    }

    /*@RequestMapping("/jobify/{email}/employer/home-page/{status}")
    public List<JobPostings> getAllJobPostingsByStatus(@PathVariable String status) {
        return jobPostingsService.getAllJobPostingsByStatus(status);
    }*/

    @RequestMapping("/{email}/employer/{status}")
    public List<JobPostings> getAllJobPostingsByEmployerAndStatus(@PathVariable String email, @PathVariable String status) {
        return jobPostingsService.getAllJobPostingsByEmployerAndStatus(email, status);
    }

    @RequestMapping("/{email}/student/job-postings/{id}")
    public String getJobPostingForStudent(@PathVariable long id, ModelMap modelMap, HttpServletRequest request) {
        JobPostings jobPosting =  jobPostingsService.getJobPosting(id);
        EmployerProfileInformation employerProfileInformation = employerProfileInformationService.getEmployer(jobPosting.getEmail());
        modelMap.addAttribute("jobPosting", jobPosting);
        modelMap.addAttribute("employer", employerProfileInformation);
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        return "/Student/ViewAJobPosting";
    }

    @RequestMapping("/{email}/employer/job-postings/{id}")
    public String getJobPostingForEmployer(@PathVariable long id, ModelMap modelMap, HttpServletRequest request) {
        JobPostings jobPosting =  jobPostingsService.getJobPosting(id);
        EmployerProfileInformation employerProfileInformation = employerProfileInformationService.getEmployer(jobPosting.getEmail());
        modelMap.addAttribute("jobPosting", jobPosting);
        modelMap.addAttribute("employer", employerProfileInformation);
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        return "/Employer/ViewCreatedJobPosting";
    }
    @RequestMapping("/{email}/employer/job-postings/{id}/interview")
    public String getJobPostingForEmployerInterview(@PathVariable long id, ModelMap modelMap, HttpServletRequest request) {
        JobPostings jobPosting =  jobPostingsService.getJobPosting(id);
        EmployerProfileInformation employerProfileInformation = employerProfileInformationService.getEmployer(jobPosting.getEmail());
        modelMap.addAttribute("jobPosting", jobPosting);
        modelMap.addAttribute("employer", employerProfileInformation);
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        return "/Employer/ViewCreatedJobPostingInterview";
    }

    @RequestMapping("/{email}/admin/job-postings/{id}")
    public String getJobPostingForAdmin(@PathVariable long id, ModelMap modelMap, HttpServletRequest request) {
        JobPostings jobPosting =  jobPostingsService.getJobPosting(id);
        EmployerProfileInformation employerProfileInformation = employerProfileInformationService.getEmployer(jobPosting.getEmail());
        modelMap.addAttribute("jobPosting", jobPosting);
        modelMap.addAttribute("employer", employerProfileInformation);
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        return "/Admin/ViewAJobPostingAdmin";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{email}/employer/add-job-posting")
    public String showAddJobPostingForm(HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        return "/Employer/AddJobPosting";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{email}/employer/add-job-posting")
    public String addJobPosting(@ModelAttribute JobPostings jobPosting, @PathVariable String email, @RequestParam("description") String description) {
        jobPosting.setDescription(description);
        jobPosting.setEmail(email);
        EmployerProfileInformation employer =  employerProfileInformationService.getEmployer(jobPosting.getEmail());
        jobPosting.setCompany(employer.getCompany());
        jobPosting.setStatus("Open");
        jobPostingsService.addJobPosting(jobPosting);
        return "redirect:/" + email + "/employer";
    }

    @RequestMapping("/employer/{id}/edit")
    public String updateJobPostingForm(HttpServletRequest request, ModelMap modelMap, @PathVariable int id) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        JobPostings jobPosting = jobPostingsService.getJobPosting(id);
        modelMap.addAttribute("jobPosting", jobPosting);
        return "/Employer/EditJobPosting";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{email}/employer/{id}/edit")
    public String updateJobPosting(JobPostings jobPosting, ModelMap modelMap, HttpServletRequest request, @PathVariable long id) {
        String email = (String) request.getSession().getAttribute("email");
        JobPostings jobPostingOld = jobPostingsService.getJobPosting(id);
        jobPosting.setId(id);
        jobPosting.setEmail(email);
        jobPosting.setCompany(jobPostingOld.getCompany());
        jobPosting.setStatus(jobPostingOld.getStatus());
        modelMap.addAttribute("email", email);
        jobPostingsService.updateJobPosting(jobPosting);
        return "redirect:/" + email + "/employer/job-postings/" + jobPosting.getId();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{email}/admin/job-postings/{id}/edit")
    public void updateJobPostingFromAdmin(@RequestBody JobPostings jobPostings) {
        jobPostingsService.updateJobPosting(jobPostings);
    }

    @RequestMapping(value = "/{email}/employer/{id}/delete")
    public String deleteJobPosting(@PathVariable long id, ModelMap modelMap, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        List<Applications> applications = applicationsService.getAllApplicationsByJobPostingId(id);
        for (Applications application : applications) {
            applicationsService.deleteApplication(application.getId());
        }
        jobPostingsService.deleteJobPosting(id);
        List<JobPostings> jobPostings = jobPostingsService.getAllJobPostingsByEmployer(email);
        modelMap.addAttribute("jobPostings", jobPostings);
        return "/Employer/viewCreatedJobPostings";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{email}/admin/job-postings/{id}/delete")
    public String deleteJobPostingFromAdmin(@PathVariable long id, ModelMap modelMap, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        List<Applications> applications = applicationsService.getAllApplicationsByJobPostingId(id);
        for (Applications application : applications) {
            applicationsService.deleteApplication(application.getId());
        }
        jobPostingsService.deleteJobPosting(id);
        return "redirect:/" + email + "/admin/job-postings";
    }
}
