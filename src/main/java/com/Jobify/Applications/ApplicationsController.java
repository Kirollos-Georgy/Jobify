package com.Jobify.Applications;

import com.Jobify.JobPostings.JobPostings;
import com.Jobify.JobPostings.JobPostingsService;
import com.Jobify.StudentProfileInformation.StudentProfileInformation;
import com.Jobify.StudentProfileInformation.StudentProfileInformationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationsController {

    private final ApplicationsService applicationsService;
    private final JobPostingsService jobPostingsService;
    private final StudentProfileInformationService studentProfileInformationService;

    public ApplicationsController(ApplicationsService applicationsService, JobPostingsService jobPostingsService, StudentProfileInformationService studentProfileInformationService) {
        this.applicationsService = applicationsService;
        this.jobPostingsService = jobPostingsService;
        this.studentProfileInformationService = studentProfileInformationService;
    }

    @RequestMapping("/admin/job-postings/{jobPostingId}/applications")
    public String getAllApplicationsByJobPostingIdForAdmin(@PathVariable long jobPostingId, ModelMap modelMap) {
        getAllApplicationsByJobPostingIdFunction(jobPostingId, modelMap);
        return "/Admin/ViewStudentApplications";
    }

    @RequestMapping("/employer/{jobPostingId}/applications")
    public String getAllApplicationsByJobPostingId(@PathVariable long jobPostingId, ModelMap modelMap) {
        getAllApplicationsByJobPostingIdFunction(jobPostingId, modelMap);
        return "/Employer/ViewStudentApplications";
    }

    private void getAllApplicationsByJobPostingIdFunction(@PathVariable long jobPostingId, ModelMap modelMap) {
        List<Applications> applications = applicationsService.getAllApplicationsByJobPostingId(jobPostingId);
        getAllApplicationsByJobPostingIdAndStatus(jobPostingId, modelMap, applications);
    }

    private void getAllApplicationsByJobPostingIdAndStatus(@PathVariable long jobPostingId, ModelMap modelMap, List<Applications> applications) {
        List<StudentProfileInformation> studentProfileInformation = new ArrayList<>();
        JobPostings jobPosting = jobPostingsService.getJobPosting(jobPostingId);
        for (Applications application : applications) {
            studentProfileInformation.add(studentProfileInformationService.getStudent(application.getStudentEmail()));
        }
        modelMap.addAttribute("studentInformation", studentProfileInformation);
        modelMap.addAttribute("jobPosting", jobPosting);
        modelMap.addAttribute("applications", applications);
    }

    @RequestMapping("/student/{status}")
    public String getAllApplicationsByStudentEmail(ModelMap modelMap, HttpServletRequest request, @PathVariable String status) {
        String email = (String) request.getSession().getAttribute("email");

        List<Applications> applications = new ArrayList<>();

        if (status.equals("applications")) {
            status = "Applied";
            applications = applicationsService.getAllApplicationsByStudentEmail(email);
        } else if (status.equals("interviews")) {
            status = "Selected for interview";
            applications = applicationsService.getAllApplicationsByStudentEmailAndStatus(email, status);
        }
        List<JobPostings> jobPostings = new ArrayList<>();
        for (Applications application : applications) {
            jobPostings.add(jobPostingsService.getJobPosting(application.getJobPostingsID()));
        }
        modelMap.addAttribute("jobPostings", jobPostings);
        return "/Student/viewJobPostings";
    }

    @RequestMapping("/employer/{jobPostingId}/interview/applications")
    public String getAllApplicationsByJobPostingIdAndStatus(@PathVariable long jobPostingId, ModelMap modelMap) {
        List<Applications> applications = applicationsService.getAllApplicationsByJobPostingIdAndStatus(jobPostingId, "Selected for interview");
        getAllApplicationsByJobPostingIdAndStatus(jobPostingId, modelMap, applications);
        return "/Employer/ViewStudentApplications";
    }

    @RequestMapping("/employer/{jobPostingId}/applications/{id}")
    public String getApplicationEmployer(@PathVariable long id, ModelMap modelMap, @PathVariable long jobPostingId) {
        getApplication(id, modelMap, jobPostingId);
        return "/Employer/ViewAStudentInformation";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/employer/{jobPostingId}/applications/{id}")
    public String selectStudentForInterview(@PathVariable long id, ModelMap modelMap, @PathVariable long jobPostingId) {
        Applications application1 = applicationsService.getApplication(id);
        application1.setStatus("Selected for interview");
        applicationsService.updateApplication(application1);

        getAllApplicationsByJobPostingIdFunction(jobPostingId, modelMap);
        return "/Employer/ViewStudentApplications";
    }

    @RequestMapping("/admin/job-postings/{jobPostingId}/applications/{id}")
    public String getApplicationAdmin(@PathVariable long id, ModelMap modelMap, @PathVariable long jobPostingId) {
        getApplication(id, modelMap, jobPostingId);
        return "/Admin/ViewAStudentInformation";
    }

    private void getApplication(@PathVariable long id, ModelMap modelMap, @PathVariable long jobPostingId) {
        Applications application = applicationsService.getApplication(id);
        JobPostings jobPosting = jobPostingsService.getJobPosting(jobPostingId);
        StudentProfileInformation student = studentProfileInformationService.getStudent(application.getStudentEmail());
        modelMap.addAttribute("applications", application);
        modelMap.addAttribute("jobPosting", jobPosting);
        modelMap.addAttribute("studentInformation", student);
    }

    @RequestMapping("/student/{id}/apply")
    public String applyForJobPostingForm(@PathVariable long id, ModelMap modelMap) {
        JobPostings jobPosting = jobPostingsService.getJobPosting(id);
        modelMap.addAttribute("jobPosting", jobPosting);
        return "/Student/ApplyForAJobPosting";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/student/{id}/apply")
    public String applyForJobPosting(HttpServletRequest request, @PathVariable long id, @RequestParam("resumeFile") MultipartFile resumeFile, @RequestParam("coverLetterFile") MultipartFile coverLetterFile, @RequestParam("unofficialTranscriptFile") MultipartFile unofficialTranscriptFile, @RequestParam(value = "default", required = false) String[] defaultInformation) {
        JobPostings jobPosting = jobPostingsService.getJobPosting(id);
        String email = (String) request.getSession().getAttribute("email");
        StudentProfileInformation studentProfileInformation = studentProfileInformationService.getStudent(email);
        Applications application = new Applications();

        boolean defaultResume = false;
        boolean defaultCoverLetter = false;
        boolean defaultTranscript = false;

        if (defaultInformation != null) {
            for (String s : defaultInformation) {
                switch (s) {
                    case "defaultResume" -> defaultResume = true;
                    case "defaultCoverLetter" -> defaultCoverLetter = true;
                    case "defaultTranscript" -> defaultTranscript = true;
                }
            }
        }
        try {
            if (!defaultResume) {
                byte[] resumeBytes = resumeFile.getBytes();
                Blob resumeBlob = new SerialBlob(resumeBytes);
                application.setResume(resumeBlob);
            } else {
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

        return "redirect:/student";
    }

    @RequestMapping("/student/job-postings/{jobPostingId}/edit")
    public String editJobPostingApplicationForm(@PathVariable long jobPostingId, ModelMap modelMap) {
        JobPostings jobPosting = jobPostingsService.getJobPosting(jobPostingId);
        modelMap.addAttribute("jobPosting", jobPosting);
        return "/Student/EditAJobPostingApplication";
    }

    @RequestMapping("/admin/job-postings/{jobPostingId}/applications/{id}/edit")
    public String editJobPostingApplicationFormFromAdmin(@PathVariable long jobPostingId, ModelMap modelMap, @PathVariable long id) {
        JobPostings jobPosting = jobPostingsService.getJobPosting(jobPostingId);
        modelMap.addAttribute("jobPosting", jobPosting);
        modelMap.addAttribute("applicationId", id);
        return "/Admin/EditAJobPostingApplication";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/student/job-postings/{jobPostingId}/edit")
    public String editJobPostingApplication(HttpServletRequest request, @PathVariable long jobPostingId, @RequestParam("resumeFile") MultipartFile resumeFile, @RequestParam("coverLetterFile") MultipartFile coverLetterFile, @RequestParam("unofficialTranscriptFile") MultipartFile unofficialTranscriptFile, @RequestParam(value = "default", required = false) String[] defaultInformation) {
        JobPostings jobPosting = jobPostingsService.getJobPosting(jobPostingId);
        String email = (String) request.getSession().getAttribute("email");
        StudentProfileInformation studentProfileInformation = studentProfileInformationService.getStudent(email);
        Applications application = new Applications();
        Applications oldApplication = applicationsService.getAllApplicationsByStudentEmailAndJobPostingId(email, jobPostingId);

        editJobPostingApplications(resumeFile, coverLetterFile, unofficialTranscriptFile, defaultInformation, jobPosting, studentProfileInformation, application, oldApplication);
        application.setStudentEmail(email);

        applicationsService.updateApplication(application);

        return "redirect:/student/job-postings/" + jobPostingId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/job-postings/{jobPostingId}/applications/{id}/edit")
    public String editJobPostingApplicationFromAdmin(@PathVariable long jobPostingId, @PathVariable long id, @RequestParam("resumeFile") MultipartFile resumeFile, @RequestParam("coverLetterFile") MultipartFile coverLetterFile, @RequestParam("unofficialTranscriptFile") MultipartFile unofficialTranscriptFile, @RequestParam(value = "default", required = false) String[] defaultInformation) {
        JobPostings jobPosting = jobPostingsService.getJobPosting(jobPostingId);
        Applications oldApplication = applicationsService.getApplication(id);
        StudentProfileInformation studentProfileInformation = studentProfileInformationService.getStudent(oldApplication.getStudentEmail());
        Applications application = new Applications();

        editJobPostingApplications(resumeFile, coverLetterFile, unofficialTranscriptFile, defaultInformation, jobPosting, studentProfileInformation, application, oldApplication);
        application.setStudentEmail(oldApplication.getStudentEmail());

        applicationsService.updateApplication(application);

        return "redirect:/admin/job-postings/" + jobPostingId + "/applications/" + id;
    }

    private void editJobPostingApplications(@RequestParam("resumeFile") MultipartFile resumeFile, @RequestParam("coverLetterFile") MultipartFile coverLetterFile, @RequestParam("unofficialTranscriptFile") MultipartFile unofficialTranscriptFile, @RequestParam(value = "default", required = false) String[] defaultInformation, JobPostings jobPosting, StudentProfileInformation studentProfileInformation, Applications application, Applications oldApplication) {
        boolean defaultResume = false;
        boolean defaultCoverLetter = false;
        boolean defaultTranscript = false;

        if (defaultInformation != null) {
            for (String s : defaultInformation) {
                switch (s) {
                    case "defaultResume" -> defaultResume = true;
                    case "defaultCoverLetter" -> defaultCoverLetter = true;
                    case "defaultTranscript" -> defaultTranscript = true;
                }
            }
        }
        try {
            if (defaultResume || !resumeFile.isEmpty()) {
                if (!defaultResume) {
                    byte[] resumeBytes = resumeFile.getBytes();
                    Blob resumeBlob = new SerialBlob(resumeBytes);
                    application.setResume(resumeBlob);
                } else {
                    application.setResume(studentProfileInformation.getResume());
                }
            } else {
                application.setResume(oldApplication.getResume());
            }
            if (defaultCoverLetter || !coverLetterFile.isEmpty()) {
                if (!defaultCoverLetter) {
                    byte[] coverLetterBytes = coverLetterFile.getBytes();
                    Blob coverLetterBlob = new SerialBlob(coverLetterBytes);
                    application.setCoverLetter(coverLetterBlob);
                } else {
                    application.setCoverLetter(studentProfileInformation.getCoverLetter());
                }
            } else {
                application.setCoverLetter(oldApplication.getCoverLetter());
            }
            if (defaultTranscript || !unofficialTranscriptFile.isEmpty()) {
                if (!defaultTranscript) {
                    byte[] unofficialTranscriptBytes = unofficialTranscriptFile.getBytes();
                    Blob unofficialTranscriptBlob = new SerialBlob(unofficialTranscriptBytes);
                    application.setUnofficialTranscript(unofficialTranscriptBlob);
                } else {
                    application.setUnofficialTranscript(studentProfileInformation.getUnofficialTranscript());
                }
            } else {
                application.setUnofficialTranscript(oldApplication.getUnofficialTranscript());
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        application.setId(oldApplication.getId());
        application.setJobPostingsID(jobPosting.getId());
        application.setStatus("Applied");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/student/job-postings/{jobPostingId}/delete")
    public String deleteApplication(@PathVariable long jobPostingId, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        Applications application = applicationsService.getAllApplicationsByStudentEmailAndJobPostingId(email, jobPostingId);
        applicationsService.deleteApplication(application.getId());
        return "redirect:/student";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/job-postings/{jobPostingId}/applications/{id}/delete")
    public String deleteApplicationFromAdmin(@PathVariable long jobPostingId, @PathVariable long id) {
        applicationsService.deleteApplication(id);
        return "redirect:/admin/job-postings/" + jobPostingId + "/applications";
    }
}