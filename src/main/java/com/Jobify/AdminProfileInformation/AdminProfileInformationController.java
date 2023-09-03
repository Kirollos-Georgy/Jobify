package com.Jobify.AdminProfileInformation;

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
public class AdminProfileInformationController {

    @Autowired
    private AdminProfileInformationService adminProfileInformationService;

    @RequestMapping("/{email}/admin/view-admins")
    public List<AdminProfileInformation> getAllAdmins() {
        return adminProfileInformationService.getAllAdmins();
    }

    @RequestMapping("/{email}/admin/profile")
    public AdminProfileInformation getAdmin(@PathVariable String email) {
        return adminProfileInformationService.getAdmin(email);
    }

    @RequestMapping("/{email}/admin/view-admins/{adminEmail}")
    public AdminProfileInformation getAdminForAdmin(@PathVariable String adminEmail) {
        return adminProfileInformationService.getAdmin(adminEmail);
    }

    @RequestMapping("/signUp/admin")
    public String adminCreationForm(HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        return "CreatingAdminProfile";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signUp/{email}/admin")
    public String addAdmin(AdminProfileInformation adminProfileInformation, HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        adminProfileInformation.setEmail(email);
        adminProfileInformationService.addAdmin(adminProfileInformation);
        return "redirect:/" + email + "/admin/feedbacks";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{email}/admin/profile/edit")
    public void updateAdmin(@RequestBody AdminProfileInformation adminProfileInformation) {
        adminProfileInformationService.updateAdmin(adminProfileInformation);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{email}/admin/view-admins/{adminEmail}/edit")
    public void updateAdminFromAdmin(@RequestBody AdminProfileInformation adminProfileInformation) {
        adminProfileInformationService.updateAdmin(adminProfileInformation);
    }

   /* @RequestMapping(method = RequestMethod.DELETE, value = "/{email}/admin/profile/delete")
    public void deleteAdmin(@PathVariable String email) {
        adminProfileInformationService.deleteAdmin(email);
    }*/

    @RequestMapping(method = RequestMethod.DELETE, value = "/{email}/admin/view-admins/{adminEmail}/delete")
    public void deleteAdminFromAdmin(@PathVariable String adminEmail) {
        adminProfileInformationService.deleteAdmin(adminEmail);
    }
}
