package com.Jobify.EmployerProfileInformation;

import com.Jobify.StudentProfileInformation.StudentProfileInformation;
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
public class EmployerProfileInformationController {

    @Autowired
    private EmployerProfileInformationService employerProfileInformationService;

    @RequestMapping("/{email}/admin/view-employers")
    public List<EmployerProfileInformation> getAllEmployers() {
        return employerProfileInformationService.getAllEmployers();
    }

    @RequestMapping("/{email}/employer/profile")
    public EmployerProfileInformation getEmployer(@PathVariable String email) {
        return employerProfileInformationService.getEmployer(email);
    }

    @RequestMapping("/{email}/admin/view-employers/{employerEmail}")
    public EmployerProfileInformation getEmployerForAdmin(@PathVariable String employerEmail) {
        return employerProfileInformationService.getEmployer(employerEmail);
    }

    /*@RequestMapping(method = RequestMethod.POST, value = "/signUp/employer")
    public String addEmployer(@RequestBody EmployerProfileInformation employerProfileInformation) {
        employerProfileInformationService.addEmployer(employerProfileInformation);
        return "redirect:/" + employerProfileInformation.getEmail() + "/employer";
    }*/

    //Works
    @GetMapping("/signUp/employer")
    public String employerCreationForm(HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        return "CreatingEmployerProfile";
    }

    //Works
    @RequestMapping(method = RequestMethod.POST, value = "/signUp/{email}/employer")
    public String addEmployer(EmployerProfileInformation employerProfileInformation, @PathVariable String email) {
        employerProfileInformation.setEmail(email);
        employerProfileInformationService.addEmployer(employerProfileInformation);
        return "redirect:/" + email + "/employer";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{email}/employer/profile/edit")
    public void updateEmployer(@RequestBody EmployerProfileInformation employerProfileInformation) {
        employerProfileInformationService.updateEmployer(employerProfileInformation);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{email}/admin/view-employers/{employerEmail}/edit")
    public void updateEmployerFromAdmin(@RequestBody EmployerProfileInformation employerProfileInformation) {
        employerProfileInformationService.updateEmployer(employerProfileInformation);
    }

    /*@RequestMapping(method = RequestMethod.DELETE, value = "/{email}/employer/profile/delete")
    public void deleteEmployer(@PathVariable String email) {
        employerProfileInformationService.deleteEmployer(email);
    }*/

    @RequestMapping(method = RequestMethod.DELETE, value = "/{email}/admin/view-employers/{employerEmail}/delete")
    public void deleteEmployerFromAdmin(@PathVariable String employerEmail) {
        employerProfileInformationService.deleteEmployer(employerEmail);
    }
}
