package com.Jobify.loginInformation;

import com.Jobify.AdminProfileInformation.AdminProfileInformation;
import com.Jobify.AdminProfileInformation.AdminProfileInformationService;
import com.Jobify.Applications.Applications;
import com.Jobify.Applications.ApplicationsService;
import com.Jobify.EmployerProfileInformation.EmployerProfileInformation;
import com.Jobify.EmployerProfileInformation.EmployerProfileInformationService;
import com.Jobify.Feedback.FeedbackService;
import com.Jobify.JobPostings.JobPostings;
import com.Jobify.JobPostings.JobPostingsService;
import com.Jobify.StudentProfileInformation.StudentProfileInformation;
import com.Jobify.StudentProfileInformation.StudentProfileInformationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginInformationController {

    @Autowired
    private LoginInformationService loginInformationService;
    @Autowired
    private StudentProfileInformationService studentProfileInformationService;
    @Autowired
    private EmployerProfileInformationService employerProfileInformationService;
    @Autowired
    private AdminProfileInformationService adminProfileInformationService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private ApplicationsService applicationsService;
    @Autowired
    private JobPostingsService jobPostingsService;

    @RequestMapping("/admin/all-users")
    public String getAllUsers(HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        List<LoginInformation> loginInformation = loginInformationService.getAllUsers();
        List<StudentProfileInformation> studentProfileInformation = new ArrayList<>();
        List<EmployerProfileInformation> employerProfileInformation = new ArrayList<>();
        List<AdminProfileInformation> adminProfileInformation = new ArrayList<>();
        String tempEmail;
        for (LoginInformation information : loginInformation) {
            tempEmail = information.getEmail();
            switch (information.getUserType()) {
                case "student" -> studentProfileInformation.add(studentProfileInformationService.getStudent(tempEmail));
                case "employer" ->
                        employerProfileInformation.add(employerProfileInformationService.getEmployer(tempEmail));
                case "admin" -> adminProfileInformation.add(adminProfileInformationService.getAdmin(tempEmail));
            }
        }
        modelMap.addAttribute("email", email);
        modelMap.addAttribute("studentInformation", studentProfileInformation);
        modelMap.addAttribute("employerInformation", employerProfileInformation);
        modelMap.addAttribute("adminInformation", adminProfileInformation);
        return "/Admin/ViewCurrentUsers";
    }

    @RequestMapping("/")
    public String welcomePage()  {
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/signUp")
    public String showCreateUserForm() {
        return "/Creating account/CreateUser";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signUp")
    public String addUser(LoginInformation loginInformation, HttpServletRequest request, @RequestParam String adminCode) {
        if (loginInformationService.emailUsed(loginInformation.getEmail())) {
            return "/Creating account/InvalidCreatingUser";
        } else {
            if (loginInformation.getUserType().equals("admin") && !adminCode.equals("QWERTY")) {
                return "/Creating account/InvalidCreatingUser";
            }
            loginInformationService.addUser(loginInformation);
            request.getSession().setAttribute("email", loginInformation.getEmail());
            return "redirect:/signUp/" + loginInformation.getUserType();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String showLoginPage() {
        return "/Login/LoginPage";
    }

    @RequestMapping("/login")
    public String signIn(LoginInformation loginInformation, HttpServletRequest request) {
        if (loginInformationService.validLoginInformation(loginInformation)) {
            LoginInformation loginInformation1 = loginInformationService.getUser(loginInformation.getEmail());
            request.getSession().setAttribute("email", loginInformation.getEmail());
            if (loginInformation1.getUserType().equals("admin")) {
                return "redirect:/" + loginInformation1.getUserType() + "/feedbacks";
            }
            return "redirect:/" + loginInformation1.getUserType();
        }
        else {
            return "/Login/InvalidLoginPage";
        }
    }

    @GetMapping("/{userType}/profile/change-password")
    public String changePasswordFrom(@PathVariable String userType, ModelMap modelMap) {
        modelMap.addAttribute("userType", userType);
        return "changePasswordForm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{userType}/profile/change-password")
    public String updateUserStudent(ModelMap modelMap, @RequestParam("oldPassword") String oldPassword, @PathVariable String userType, @RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword, @RequestParam("email") String confirmEmail, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        LoginInformation oldLoginInformation = loginInformationService.getUser(email);
        if (oldLoginInformation.getPassword().equals(oldPassword)) {
            if (email.equals(confirmEmail)) {
                if (password.equals(confirmPassword)) {
                    LoginInformation loginInformation = new LoginInformation(email, password, userType);
                    loginInformationService.updateUser(loginInformation);
                    return "redirect:/" + userType + "/profile";
                }
                else {
                    modelMap.addAttribute("error", "passwordNotMatching");
                }
            }
            else {
                modelMap.addAttribute("error", "incorrectEmail");
            }
        }
        else {
            modelMap.addAttribute("error", "incorrectOldPassword");
        }
        modelMap.addAttribute("userType", userType);
        return "invalidChangePasswordForm";
    }

    @GetMapping("/delete")
    public String deleteUser(HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        LoginInformation loginInformation = loginInformationService.getUser(email);
        switch (loginInformation.getUserType()) {
            case "student" -> {
                List<Applications> applications = applicationsService.getAllApplicationsByStudentEmail(email);
                for (Applications application : applications) {
                    applicationsService.deleteApplication(application.getId());
                }
                feedbackService.deleteFeedback(email);
                studentProfileInformationService.deleteStudent(email);
            }
            case "employer" -> {
                List<JobPostings> jobPostings = jobPostingsService.getAllJobPostingsByEmployer(email);
                for (JobPostings jobPosting : jobPostings) {
                    jobPostingsService.deleteJobPosting(jobPosting.getId());
                }
                feedbackService.deleteFeedback(email);
                employerProfileInformationService.deleteEmployer(email);
            }
            case "admin" -> adminProfileInformationService.deleteAdmin(email);
        }
        loginInformationService.deleteUser(email);
        return "index";
    }
}