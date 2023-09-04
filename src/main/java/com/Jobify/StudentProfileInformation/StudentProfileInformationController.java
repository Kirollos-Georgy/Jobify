package com.Jobify.StudentProfileInformation;

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

    @RequestMapping("/{email}/admin/students")
    public String getAllStudents(ModelMap modelMap) {
        List<StudentProfileInformation> students = studentProfileInformationService.getAllStudents();
        modelMap.addAttribute("students", students);
        return "ViewAllStudents";
    }

    @GetMapping("/{email}/student/profile")
    public String getStudent(@PathVariable String email, ModelMap modelMap, HttpServletRequest request) {
        StudentProfileInformation student = studentProfileInformationService.getStudent(email);
        modelMap.addAttribute("studentInformation", student);
        String email1 = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email1);
        return "StudentHomePage";
    }


    @RequestMapping("/{email}/admin/all-users/student/{studentEmail}")
    public String getStudentForAdmin(@PathVariable String studentEmail, ModelMap modelMap, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        StudentProfileInformation student = studentProfileInformationService.getStudent(studentEmail);
        modelMap.addAttribute("studentInformation", student);
        return "ViewStudentUserProfile";
    }

    //Works
    @GetMapping("/signUp/student")
    public String studentCreationForm(HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        return "CreatingUserProfile";
    }

    //Works
    @RequestMapping(method = RequestMethod.POST, value = "/signUp/{email}/student")
    public String addStudent(StudentProfileInformation studentProfileInformation, @PathVariable String email, @RequestParam("resumeFile") MultipartFile resumeFile, @RequestParam("coverLetterFile") MultipartFile coverLetterFile, @RequestParam("unofficialTranscriptFile") MultipartFile unofficialTranscriptFile, @RequestParam("profilePictureFile") MultipartFile profilePictureFile) {
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
        return "redirect:/" + email + "/student";
    }

    @GetMapping("/student/profile/edit")
    public String studentEditingForm(HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        StudentProfileInformation student = studentProfileInformationService.getStudent(email);
        modelMap.addAttribute("studentInformation", student);
        return "EditingUserProfile";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{email}/student/profile/edit")
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
        return "redirect:/" + email + "/student/profile";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{email}/admin/students/{studentEmail}/edit")
    public String updateStudentFromAdmin(@RequestBody StudentProfileInformation studentProfileInformation, @PathVariable String studentEmail, @PathVariable String email) {
        studentProfileInformation.setEmail(studentEmail);
        studentProfileInformationService.updateStudent(studentProfileInformation);
        return "redirect:/" + email + "/admin/student/" + studentEmail;
    }

    /*@RequestMapping(method = RequestMethod.DELETE, value = "/{email}/student/profile/delete")
    public void deleteStudent(@PathVariable String email) {
        studentProfileInformationService.deleteStudent(email);
    }*/

    @RequestMapping(method = RequestMethod.DELETE, value = "/{email}/admin/students/{studentEmail}/delete")
    public String deleteStudentFromAdmin(@PathVariable String studentEmail, @PathVariable String email) {
        studentProfileInformationService.deleteStudent(studentEmail);
        return "redirect:/" + email + "/admin/students/";
    }
}
