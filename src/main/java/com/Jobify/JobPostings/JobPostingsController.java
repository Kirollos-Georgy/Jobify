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
    @Autowired
    private StudentProfileInformationService studentProfileInformationService;
    @Autowired
    private ApplicationsService applicationsService;

    //Works - not tested with multiple job postings
    @RequestMapping("/{email}/student")
    public String getAllJobPostingsForStudent(ModelMap modelMap, HttpServletRequest request) {
        List<JobPostings> jobPostings = jobPostingsService.getAllJobPostings();
        modelMap.addAttribute("jobPosting", jobPostings);
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        System.out.println(email);
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

    @RequestMapping("/{email}/student/{id}")
    public String getJobPostingForStudent(@PathVariable long id, ModelMap modelMap, HttpServletRequest request) {
        JobPostings jobPosting =  jobPostingsService.getJobPosting(id);
        EmployerProfileInformation employerProfileInformation = employerProfileInformationService.getEmployer(jobPosting.getEmail());
        modelMap.addAttribute("jobPosting", jobPosting);
        modelMap.addAttribute("employer", employerProfileInformation);
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        return "ViewAJobPosting";
    }

    @RequestMapping("/{email}/student/{id}/apply")
    public String applyForJobPostingForm(@PathVariable long id, ModelMap modelMap, HttpServletRequest request) {
        JobPostings jobPosting =  jobPostingsService.getJobPosting(id);
        modelMap.addAttribute("jobPosting", jobPosting);
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        return "ApplyForAJobPosting";
    }

    @RequestMapping(method=RequestMethod.POST, value="/{email}/student/{id}/apply")
    public String applyForJobPosting(HttpServletRequest request, @PathVariable long id, ModelMap modelMap, @RequestParam("resumeFile") MultipartFile resumeFile, @RequestParam("coverLetterFile") MultipartFile coverLetterFile, @RequestParam("unofficialTranscriptFile") MultipartFile unofficialTranscriptFile, @RequestParam("default") String[] defaultInformation) {
        JobPostings jobPosting =  jobPostingsService.getJobPosting(id);
        String email = (String) request.getSession().getAttribute("email");
        StudentProfileInformation studentProfileInformation = studentProfileInformationService.getStudent(email);
        Applications application = new Applications();

        boolean defaultResume = false;
        boolean defaultCoverLetter = false;
        boolean defaultTranscript = false;

        for (String s : defaultInformation) {
            switch (s) {
                case "defaultResume" -> defaultResume = true;
                case "defaultCoverLetter" -> defaultCoverLetter = true;
                case "defaultTranscript" -> defaultTranscript = true;
            }
        }
        try {
            if(!defaultResume) {
                byte[] resumeBytes = resumeFile.getBytes();
                Blob resumeBlob = new SerialBlob(resumeBytes);
                application.setResume(resumeBlob);
            }
            else {
                application.setResume(studentProfileInformation.getResume());
            }
            if (!defaultCoverLetter) {
                byte[] coverLetterBytes = coverLetterFile.getBytes();
                Blob coverLetterBlob = new SerialBlob(coverLetterBytes);
                application.setCoverLetter(coverLetterBlob);
            }
            else {
                application.setCoverLetter(studentProfileInformation.getCoverLetter());
            }
            if (!defaultTranscript) {
                byte[] unofficialTranscriptBytes = unofficialTranscriptFile.getBytes();
                Blob unofficialTranscriptBlob = new SerialBlob(unofficialTranscriptBytes);
                application.setUnofficialTranscript(unofficialTranscriptBlob);
            }
            else {
                application.setUnofficialTranscript(studentProfileInformation.getUnofficialTranscript());
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        application.setJobPostingsID(jobPosting.getId());
        application.setStatus("Applied");
        application.setStudentEmail(email);

        applicationsService.addApplication(application);

        return "redirect:/" + email + "/student";
    }

    @RequestMapping("/{email}/employer/{id}")
    public JobPostings getJobPostingForEmployer(@PathVariable long id) {
        return jobPostingsService.getJobPosting(id);
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
