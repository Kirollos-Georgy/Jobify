package com.Jobify.JobPostings;

import com.Jobify.Applications.Applications;
import com.Jobify.Applications.ApplicationsService;
import com.Jobify.EmployerProfileInformation.EmployerProfileInformation;
import com.Jobify.EmployerProfileInformation.EmployerProfileInformationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class JobPostingsController {

    private final JobPostingsService jobPostingsService;
    private final EmployerProfileInformationService employerProfileInformationService;
    private final ApplicationsService applicationsService;

    public JobPostingsController(JobPostingsService jobPostingsService, EmployerProfileInformationService employerProfileInformationService, ApplicationsService applicationsService) {
        this.jobPostingsService = jobPostingsService;
        this.employerProfileInformationService = employerProfileInformationService;
        this.applicationsService = applicationsService;
    }

    @RequestMapping("/student")
    public String getAllJobPostingsForStudent(ModelMap modelMap) {
        List<JobPostings> jobPostings = jobPostingsService.getAllJobPostings();
        modelMap.addAttribute("jobPostings", jobPostings);
        return "/Student/viewJobPostings";
    }

    @RequestMapping("/admin/job-postings")
    public String getAllJobPostingsForAdmin(ModelMap modelMap) {
        List<JobPostings> jobPostings = jobPostingsService.getAllJobPostings();
        modelMap.addAttribute("jobPostings", jobPostings);
        return "/Admin/viewJobPostingsAdmin";
    }

    @RequestMapping("/employer")
    public String getAllJobPostingsByEmployer(ModelMap modelMap, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        List<JobPostings> jobPostings = jobPostingsService.getAllJobPostingsByEmployer(email);
        modelMap.addAttribute("jobPostings", jobPostings);
        return "/Employer/viewCreatedJobPostings";
    }

    @RequestMapping("/employer/interview")
    public String getAllJobPostingsByEmployerInterview(HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        List<JobPostings> jobPostings = jobPostingsService.getAllJobPostingsByEmployer(email);
        modelMap.addAttribute("jobPostings", jobPostings);
        return "/Employer/viewCreatedJobPostingsInterview";
    }

    @RequestMapping("/student/job-postings/{id}")
    public String getJobPostingForStudent(@PathVariable long id, ModelMap modelMap, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        String status = null;
        Applications applications = null;
        try {
            applications = applicationsService.getAllApplicationsByStudentEmailAndJobPostingId(email, id);
        } catch (NoSuchElementException ignored) {
        }
        if (applications != null) {
            status = applications.getStatus();
        }
        JobPostings jobPosting = jobPostingsService.getJobPosting(id);
        EmployerProfileInformation employerProfileInformation = employerProfileInformationService.getEmployer(jobPosting.getEmail());
        modelMap.addAttribute("jobPosting", jobPosting);
        modelMap.addAttribute("employer", employerProfileInformation);
        modelMap.addAttribute("status", status);
        modelMap.addAttribute("applications", applications);
        return "/Student/ViewAJobPosting";
    }

    @RequestMapping("/employer/job-postings/{id}")
    public String getJobPostingForEmployer(@PathVariable long id, ModelMap modelMap) {
        JobPostings jobPosting = jobPostingsService.getJobPosting(id);
        EmployerProfileInformation employerProfileInformation = employerProfileInformationService.getEmployer(jobPosting.getEmail());
        modelMap.addAttribute("jobPosting", jobPosting);
        modelMap.addAttribute("employer", employerProfileInformation);
        return "/Employer/ViewCreatedJobPosting";
    }

    @RequestMapping("/admin/job-postings/{id}")
    public String getJobPostingForAdmin(@PathVariable long id, ModelMap modelMap) {
        JobPostings jobPosting = jobPostingsService.getJobPosting(id);
        EmployerProfileInformation employerProfileInformation = employerProfileInformationService.getEmployer(jobPosting.getEmail());
        modelMap.addAttribute("jobPosting", jobPosting);
        modelMap.addAttribute("employer", employerProfileInformation);
        return "/Admin/ViewAJobPostingAdmin";
    }

    @RequestMapping("/employer/job-postings/{id}/interview")
    public String getJobPostingForEmployerInterview(@PathVariable long id, ModelMap modelMap) {
        JobPostings jobPosting = jobPostingsService.getJobPosting(id);
        EmployerProfileInformation employerProfileInformation = employerProfileInformationService.getEmployer(jobPosting.getEmail());
        modelMap.addAttribute("jobPosting", jobPosting);
        modelMap.addAttribute("employer", employerProfileInformation);
        return "/Employer/ViewCreatedJobPostingInterview";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/employer/add-job-posting")
    public String showAddJobPostingForm() {
        return "/Employer/AddJobPosting";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/employer/add-job-posting")
    public String addJobPosting(HttpServletRequest request, @ModelAttribute JobPostings jobPosting, @RequestParam("description") String description) {
        String email = (String) request.getSession().getAttribute("email");
        jobPosting.setDescription(description);
        jobPosting.setEmail(email);
        EmployerProfileInformation employer =  employerProfileInformationService.getEmployer(jobPosting.getEmail());
        jobPosting.setCompany(employer.getCompany());
        jobPosting.setStatus("Open");
        jobPostingsService.addJobPosting(jobPosting);
        return "redirect:/employer";
    }

    @RequestMapping("/employer/{id}/edit")
    public String updateJobPostingForm(ModelMap modelMap, @PathVariable int id) {
        JobPostings jobPosting = jobPostingsService.getJobPosting(id);
        modelMap.addAttribute("jobPosting", jobPosting);
        return "/Employer/EditJobPosting";
    }

    @RequestMapping("/admin/job-postings/{id}/edit")
    public String updateJobPostingFormFromAdmin(ModelMap modelMap, @PathVariable int id) {
        JobPostings jobPosting = jobPostingsService.getJobPosting(id);
        modelMap.addAttribute("jobPosting", jobPosting);
        return "/Admin/EditJobPosting";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/employer/{id}/edit")
    public String updateJobPosting(JobPostings jobPosting, HttpServletRequest request, @PathVariable long id) {
        String email = (String) request.getSession().getAttribute("email");
        JobPostings jobPostingOld = jobPostingsService.getJobPosting(id);
        jobPosting.setId(id);
        jobPosting.setEmail(email);
        jobPosting.setCompany(jobPostingOld.getCompany());
        jobPosting.setStatus(jobPostingOld.getStatus());
        jobPostingsService.updateJobPosting(jobPosting);
        return "redirect:/employer/job-postings/" + jobPosting.getId();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/job-postings/{id}/edit")
    public String updateJobPostingFromAdmin(JobPostings jobPosting, @PathVariable long id) {
        JobPostings jobPostingOld = jobPostingsService.getJobPosting(id);
        jobPosting.setId(id);
        jobPosting.setEmail(jobPostingOld.getEmail());
        jobPosting.setCompany(jobPostingOld.getCompany());
        jobPosting.setStatus(jobPostingOld.getStatus());
        jobPostingsService.updateJobPosting(jobPosting);
        return "redirect:/admin/job-postings/" + jobPosting.getId();
    }

    @RequestMapping(value = "/employer/{id}/delete")
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

    @RequestMapping(method = RequestMethod.POST, value = "/admin/job-postings/{id}/delete")
    public String deleteJobPostingFromAdmin(@PathVariable long id) {
        List<Applications> applications = applicationsService.getAllApplicationsByJobPostingId(id);
        for (Applications application : applications) {
            applicationsService.deleteApplication(application.getId());
        }
        jobPostingsService.deleteJobPosting(id);
        return "redirect:/admin/job-postings";
    }
}