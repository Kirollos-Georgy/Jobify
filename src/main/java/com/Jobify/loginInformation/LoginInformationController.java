package com.Jobify.loginInformation;

import com.Jobify.loginInformation.LoginInformation;
import com.Jobify.loginInformation.LoginInformationService;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.result.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
//@RestController
public class LoginInformationController {

    @Autowired
    private LoginInformationService loginInformationService;

    /*@RequestMapping("/jobify/{email}/admin/all-users/signUp")
    public List<LoginInformation> getAllUsers() {
        return loginInformationService.getAllUsers();
    }*/

   /* @RequestMapping("/jobify/signUp/{email}")
    public LoginInformation getUser(@PathVariable String email) {
        return loginInformationService.getUser(email);
    }*/

    ///Works
    @RequestMapping("/")
    public String welcomePage()  {
        return "index";
    }

    //Works
    @RequestMapping(method = RequestMethod.GET, value = "/signUp")
    public String showCreateUserForm() {
        return "CreateUser";
    }

    //Works
    @RequestMapping(method = RequestMethod.POST, value = "/signUp")
    public String addUser(LoginInformation loginInformation, ModelMap modelMap, HttpServletRequest request, @RequestParam String adminCode) {
        if (loginInformationService.emailUsed(loginInformation.getEmail())) {
            return "InvalidCreatingUser"; //Tested
        }
        else {
            if (loginInformation.getUserType().equals("admin") && !adminCode.equals("QWERTY")) {
                return "InvalidCreatingUser";
            }
            loginInformationService.addUser(loginInformation);
            request.getSession().setAttribute("email", loginInformation.getEmail());
            return "redirect:/signUp/" /*+ loginInformation.getEmail() + "/"*/ + loginInformation.getUserType();
        }
    }

    //Works
    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String showLoginPage() {
        return "LoginPage";
    }

    //Works
    @RequestMapping("/login")
    public String signIn(LoginInformation loginInformation, HttpServletRequest request) {
        if (loginInformationService.validLoginInformation(loginInformation)) {
            LoginInformation loginInformation1 = loginInformationService.getUser(loginInformation.getEmail());
            request.getSession().setAttribute("email", loginInformation.getEmail());
            return "redirect:/" + loginInformation.getEmail() + "/" + loginInformation1.getUserType();
        }
        else {
            return "InvalidLoginPage"; //Tested
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{email}/{userType}/profile/change-password")
    public String updateUserStudent(@RequestBody String password, @PathVariable String email) {
        LoginInformation loginInformation = loginInformationService.getUser(email);
        loginInformation.setPassword(password);
        loginInformationService.updateUser(loginInformation);
        return "redirect:/" + loginInformation.getEmail() + "/" + loginInformation.getUserType() + "/profile";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{email}/{userType}/profile/delete")
    public String deleteUser(@PathVariable String email) {
        loginInformationService.deleteUser(email);
        return "index";
    }
}
