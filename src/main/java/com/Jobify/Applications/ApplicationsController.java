package com.Jobify.Applications;

import com.Jobify.JobPostings.JobPostings;
import com.Jobify.JobPostings.JobPostingsService;
import com.Jobify.StudentProfileInformation.StudentProfileInformation;
import com.Jobify.StudentProfileInformation.StudentProfileInformationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationsController {

    @Autowired
    private ApplicationsService applicationsService;
    @Autowired
    private JobPostingsService jobPostingsService;
    @Autowired
    private StudentProfileInformationService studentProfileInformationService;

    @RequestMapping("/{email}/admin/applications")
    public List<Applications> getAllApplications() {
        return applicationsService.getAllApplications();
    }

    @RequestMapping("/{email}/student/{status}")
    public String getAllApplicationsByStudentEmail(ModelMap modelMap, HttpServletRequest request, @PathVariable String status) {
        String email = (String) request.getSession().getAttribute("email");

        if (status.equals("applications")) {
            status = "Applied";
        } else if (status.equals("interviews")) {
            status = "Selected for interview";
        }
        List<Applications> applications = applicationsService.getAllApplicationsByStudentEmailAndStatus(email, status);
        List<JobPostings> jobPostings = new ArrayList<>();
        for (Applications application : applications) {
            jobPostings.add(jobPostingsService.getJobPosting(application.getJobPostingsID()));
        }
        modelMap.addAttribute("jobPosting", jobPostings);
        modelMap.addAttribute("email", email);
        return "viewJobPostings";
    }

    @RequestMapping("/{email}/employer/{jobPostingId}/applications")
    public String getAllApplicationsByJobPostingId(@PathVariable long jobPostingId, ModelMap modelMap, HttpServletRequest request) {
        List<Applications> applications = applicationsService.getAllApplicationsByJobPostingId(jobPostingId);
        List<StudentProfileInformation> studentProfileInformation = new ArrayList<>();
        String email = (String) request.getSession().getAttribute("email");
        for (Applications application : applications) {
            studentProfileInformation.add(studentProfileInformationService.getStudent(application.getStudentEmail()));
        }
        modelMap.addAttribute("email", email);
        modelMap.addAttribute("studentInformation", studentProfileInformation);
        return "ViewStudentApplications";
    }

   /* @RequestMapping("/jobify/student/applications/{status}")
    public List<Applications> getAllApplicationsByStatus(@PathVariable String status) {
        return applicationsService.getAllApplicationsByStatus(status);
    }*/

    @RequestMapping("/{email}/employer/{jobPostingId}/applications/selected-for-interview")
    public List<Applications> getAllApplicationsByJobPostingIdAndStatus(@PathVariable long jobPostingId, @PathVariable String status) {
        return applicationsService.getAllApplicationsByJobPostingIdAndStatus(jobPostingId, status);
    }

    @RequestMapping("/{email}/student/applications/{id}")
    public Applications getApplicationStudent(@PathVariable long id) {
        return applicationsService.getApplication(id);
    }

    @RequestMapping("/{email}/student/applications/{status}/{id}")
    public Applications getApplicationStudentWithStatus(@PathVariable long id) {
        return applicationsService.getApplication(id);
    }

    @RequestMapping("/{email}/employer/{jobPostingId}/applications/{id}")
    public Applications getApplicationEmployer(@PathVariable long id) {
        return applicationsService.getApplication(id);
    }

    @RequestMapping("/{email}/employer/{jobPostingId}/applications/selected-for-interview/{id}")
    public Applications getApplicationEmployerWithStatus(@PathVariable long id) {
        return applicationsService.getApplication(id);
    }

    @RequestMapping("/{email}/admin/applications/{id}")
    public Applications getApplicationForAdmin(@PathVariable long id) {
        return applicationsService.getApplication(id);
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

    @RequestMapping(method = RequestMethod.PUT, value = "/{email}/student/applications/{jobPostingId}/{id}/edit")
    public void updateApplication(@RequestBody Applications application) {
        applicationsService.updateApplication(application);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{email}/student/applications/{jobPostingId}/{id}/delete")
    public void deleteApplication(@PathVariable long id) {
        applicationsService.deleteApplication(id);
    }
}
