package com.Jobify.StudentProfileInformation;

import com.Jobify.Applications.Applications;
import com.Jobify.Applications.ApplicationsService;
import com.Jobify.Feedback.FeedbackService;
import com.Jobify.loginInformation.LoginInformationService;
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
import java.util.List;

@Controller
public class StudentProfileInformationController {

    @Autowired
    private StudentProfileInformationService studentProfileInformationService;
    @Autowired
    private ApplicationsService applicationsService;
    @Autowired
    private LoginInformationService loginInformationService;
    @Autowired
    FeedbackService feedbackService;

    //Not currently using it
    @RequestMapping("/admin/students")
    public String getAllStudents(ModelMap modelMap) {
        List<StudentProfileInformation> students = studentProfileInformationService.getAllStudents();
        modelMap.addAttribute("students", students);
        return "/Admin/ViewAllStudents";
    }

    @GetMapping("/student/profile")
    public String getStudent(ModelMap modelMap, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        StudentProfileInformation student = studentProfileInformationService.getStudent(email);
        modelMap.addAttribute("studentInformation", student);
        modelMap.addAttribute("email", email);
        return "/Student/StudentHomePage";
    }


    @RequestMapping("/admin/all-users/student/{studentEmail}")
    public String getStudentForAdmin(@PathVariable String studentEmail, ModelMap modelMap, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        StudentProfileInformation student = studentProfileInformationService.getStudent(studentEmail);
        modelMap.addAttribute("studentInformation", student);
        return "/Admin/ViewStudentUserProfile";
    }

    @GetMapping("/signUp/student")
    public String studentCreationForm(HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        return "/Creating account/CreatingUserProfile";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signUp/student")
    public String addStudent(StudentProfileInformation studentProfileInformation, HttpServletRequest request, @RequestParam("resumeFile") MultipartFile resumeFile, @RequestParam("coverLetterFile") MultipartFile coverLetterFile, @RequestParam("unofficialTranscriptFile") MultipartFile unofficialTranscriptFile, @RequestParam("profilePictureFile") MultipartFile profilePictureFile) {
        String email = (String) request.getSession().getAttribute("email");
        studentProfileInformation.setEmail(email);

        try {
            byte[] resumeBytes = resumeFile.getBytes();
            byte[] coverLetterBytes = coverLetterFile.getBytes();
            byte[] unofficialTranscriptBytes = unofficialTranscriptFile.getBytes();
            byte[] profilePictureBytes = profilePictureFile.getBytes();

            Blob resumeBlob = new SerialBlob(resumeBytes);
            Blob coverLetterBlob = new SerialBlob(coverLetterBytes);
            Blob unofficialTranscriptBlob = new SerialBlob(unofficialTranscriptBytes);
            Blob profilePictureBlob = new SerialBlob(profilePictureBytes);

            studentProfileInformation.setResume(resumeBlob);
            studentProfileInformation.setCoverLetter(coverLetterBlob);
            studentProfileInformation.setUnofficialTranscript(unofficialTranscriptBlob);
            studentProfileInformation.setProfilePicture(profilePictureBlob);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        studentProfileInformationService.addStudent(studentProfileInformation);
        return "redirect:/student";
    }

    @GetMapping("/student/profile/edit")
    public String studentEditingForm(HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        StudentProfileInformation student = studentProfileInformationService.getStudent(email);
        modelMap.addAttribute("studentInformation", student);
        return "/Student/EditingUserProfile";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/student/profile/edit")
    public String updateStudent(HttpServletRequest request, StudentProfileInformation studentProfileInformation, @RequestParam("resumeFile") MultipartFile resumeFile, @RequestParam("coverLetterFile") MultipartFile coverLetterFile, @RequestParam("unofficialTranscriptFile") MultipartFile unofficialTranscriptFile, @RequestParam("profilePictureFile") MultipartFile profilePictureFile) {
        String email = (String) request.getSession().getAttribute("email");
        studentProfileInformation.setEmail(email);
        StudentProfileInformation studentProfileInformation1 = studentProfileInformationService.getStudent(email);
        try {
            if (!resumeFile.isEmpty()) {
                byte[] resumeBytes = resumeFile.getBytes();
                Blob resumeBlob = new SerialBlob(resumeBytes);
                studentProfileInformation.setResume(resumeBlob);
            }
            else {
                studentProfileInformation.setResume(studentProfileInformation1.getResume());
            }
            if (!coverLetterFile.isEmpty()) {
                byte[] coverLetterBytes = coverLetterFile.getBytes();
                Blob coverLetterBlob = new SerialBlob(coverLetterBytes);
                studentProfileInformation.setCoverLetter(coverLetterBlob);
            }
            else {
                studentProfileInformation.setCoverLetter(studentProfileInformation1.getCoverLetter());
            }
            if (!unofficialTranscriptFile.isEmpty()) {
                byte[] unofficialTranscriptBytes = unofficialTranscriptFile.getBytes();
                Blob unofficialTranscriptBlob = new SerialBlob(unofficialTranscriptBytes);
                studentProfileInformation.setUnofficialTranscript(unofficialTranscriptBlob);
            }
            else {
                studentProfileInformation.setUnofficialTranscript(studentProfileInformation1.getUnofficialTranscript());
            }
            if (!profilePictureFile.isEmpty()) {
                byte[] profilePictureBytes = profilePictureFile.getBytes();
                Blob profilePictureBlob = new SerialBlob(profilePictureBytes);
                studentProfileInformation.setProfilePicture(profilePictureBlob);
            }
            else {
                studentProfileInformation.setProfilePicture(studentProfileInformation1.getProfilePicture());
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        studentProfileInformationService.updateStudent(studentProfileInformation);
        return "redirect:/student/profile";
    }

    @GetMapping("/admin/all-users/{studentEmail}/edit")
    public String studentEditingFormFromAdmin(HttpServletRequest request, ModelMap modelMap, @PathVariable String studentEmail) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        StudentProfileInformation student = studentProfileInformationService.getStudent(studentEmail);
        modelMap.addAttribute("studentInformation", student);
        return "/Admin/EditingUserProfileAdmin";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/all-users/{studentEmail}/edit")
    public String updateStudentFromAdmin(@PathVariable String studentEmail, StudentProfileInformation studentProfileInformation, @RequestParam("resumeFile") MultipartFile resumeFile, @RequestParam("coverLetterFile") MultipartFile coverLetterFile, @RequestParam("unofficialTranscriptFile") MultipartFile unofficialTranscriptFile, @RequestParam("profilePictureFile") MultipartFile profilePictureFile) {
        studentProfileInformation.setEmail(studentEmail);
        StudentProfileInformation studentProfileInformation1 = studentProfileInformationService.getStudent(studentEmail);
        try {
            if (!resumeFile.isEmpty()) {
                byte[] resumeBytes = resumeFile.getBytes();
                Blob resumeBlob = new SerialBlob(resumeBytes);
                studentProfileInformation.setResume(resumeBlob);
            } else {
                studentProfileInformation.setResume(studentProfileInformation1.getResume());
            }
            if (!coverLetterFile.isEmpty()) {
                byte[] coverLetterBytes = coverLetterFile.getBytes();
                Blob coverLetterBlob = new SerialBlob(coverLetterBytes);
                studentProfileInformation.setCoverLetter(coverLetterBlob);
            } else {
                studentProfileInformation.setCoverLetter(studentProfileInformation1.getCoverLetter());
            }
            if (!unofficialTranscriptFile.isEmpty()) {
                byte[] unofficialTranscriptBytes = unofficialTranscriptFile.getBytes();
                Blob unofficialTranscriptBlob = new SerialBlob(unofficialTranscriptBytes);
                studentProfileInformation.setUnofficialTranscript(unofficialTranscriptBlob);
            } else {
                studentProfileInformation.setUnofficialTranscript(studentProfileInformation1.getUnofficialTranscript());
            }
            if (!profilePictureFile.isEmpty()) {
                byte[] profilePictureBytes = profilePictureFile.getBytes();
                Blob profilePictureBlob = new SerialBlob(profilePictureBytes);
                studentProfileInformation.setProfilePicture(profilePictureBlob);
            } else {
                studentProfileInformation.setProfilePicture(studentProfileInformation1.getProfilePicture());
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        studentProfileInformationService.updateStudent(studentProfileInformation);
        return "redirect:/admin/all-users/student/" + studentEmail;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/all-users/student/{studentEmail}/delete")
    public String deleteStudentFromAdmin(@PathVariable String studentEmail, HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        List<Applications> applications = applicationsService.getAllApplicationsByStudentEmail(studentEmail);
        for (Applications application : applications) {
            applicationsService.deleteApplication(application.getId());
        }
        feedbackService.deleteFeedback(studentEmail);
        studentProfileInformationService.deleteStudent(studentEmail);
        loginInformationService.deleteUser(studentEmail);
        modelMap.addAttribute("email", email);
        return "redirect:/admin/all-users";
    }
}