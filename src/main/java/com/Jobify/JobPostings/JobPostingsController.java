package com.Jobify.JobPostings;

import com.Jobify.Applications.Applications;
import com.Jobify.Applications.ApplicationsService;
import com.Jobify.EmployerProfileInformation.EmployerProfileInformation;
import com.Jobify.EmployerProfileInformation.EmployerProfileInformationService;
import com.Jobify.StudentProfileInformation.StudentProfileInformation;
import com.Jobify.StudentProfileInformation.StudentProfileInformationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Controller
//@RestController
public class JobPostingsController {

    @Autowired
    private JobPostingsService jobPostingsService;
    @Autowired
    private EmployerProfileInformationService employerProfileInformationService;

    //Works - not tested with multiple job postings
    @RequestMapping("/{email}/student")
    public String getAllJobPostingsForStudent(ModelMap modelMap, HttpServletRequest request) {
        List<JobPostings> jobPostings = jobPostingsService.getAllJobPostings();
        modelMap.addAttribute("jobPosting", jobPostings);
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        return "viewJobPostings";
    }


    @RequestMapping("/{email}/admin/job-postings")
    public List<JobPostings> getAllJobPostingsForAdmin() {
        return jobPostingsService.getAllJobPostings();
    }

    //Works - not tested with multiple job postings
    @RequestMapping("/{email}/employer")
    public String getAllJobPostingsByEmployer(@PathVariable String email, ModelMap modelMap) {
        List<JobPostings> jobPostings = jobPostingsService.getAllJobPostingsByEmployer(email);
        modelMap.addAttribute("jobPostings", jobPostings);
        return "viewCreatedJobPostings";
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
        return "ViewAJobPosting";
    }

    @RequestMapping("/{email}/employer/job-postings/{id}")
    public String getJobPostingForEmployer(@PathVariable long id, ModelMap modelMap, HttpServletRequest request) {
        JobPostings jobPosting =  jobPostingsService.getJobPosting(id);
        EmployerProfileInformation employerProfileInformation = employerProfileInformationService.getEmployer(jobPosting.getEmail());
        modelMap.addAttribute("jobPosting", jobPosting);
        modelMap.addAttribute("employer", employerProfileInformation);
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        return "ViewCreatedJobPosting";
    }

    @RequestMapping("/{email}/admin/job-postings/{id}")
    public JobPostings getJobPostingForAdmin(@PathVariable long id) {
        return jobPostingsService.getJobPosting(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{email}/employer/add-job-posting")
    public String showAddJobPostingForm(HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        return "AddJobPosting";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{email}/employer/add-job-posting")
    public String addJobPosting(@ModelAttribute JobPostings jobPosting, @PathVariable String email, @RequestParam("description") String description) {
        jobPosting.setDescription(description);
        jobPosting.setEmail(email);
        EmployerProfileInformation employer =  employerProfileInformationService.getEmployer(jobPosting.getEmail());
        jobPosting.setCompany(employer.getCompany());
        jobPosting.setStatus("Open");
        jobPostingsService.addJobPosting(jobPosting);
        return "viewCreatedJobPostings";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{email}/employer/{id}/edit")
    public void updateJobPosting(@RequestBody JobPostings jobPostings) {
        jobPostingsService.updateJobPosting(jobPostings);
    }
    @RequestMapping("/employer/{id}/edit")
    public String updateJobPostingForm(HttpServletRequest request, ModelMap modelMap, @PathVariable int id) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        JobPostings jobPosting = jobPostingsService.getJobPosting(id);
        modelMap.addAttribute("jobPosting", jobPosting);
        return "EditJobPosting";
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

    @RequestMapping(method = RequestMethod.DELETE, value = "/{email}/employer/{id}/delete")
    public void deleteJobPosting(@PathVariable long id) {
        jobPostingsService.deleteJobPosting(id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{email}/admin/job-postings/{id}/delete")
    public void deleteJobPostingFromAdmin(@PathVariable long id) {
        jobPostingsService.deleteJobPosting(id);
    }
}
