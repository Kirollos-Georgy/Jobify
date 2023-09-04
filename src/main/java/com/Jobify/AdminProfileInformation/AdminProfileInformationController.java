package com.Jobify.AdminProfileInformation;

import com.Jobify.loginInformation.LoginInformationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AdminProfileInformationController {

    @Autowired
    private AdminProfileInformationService adminProfileInformationService;
    @Autowired
    private LoginInformationService loginInformationService;

    @RequestMapping("/{email}/admin/view-admins")
    public List<AdminProfileInformation> getAllAdmins() {
        return adminProfileInformationService.getAllAdmins();
    }


    @RequestMapping("/{email}/admin/profile")
    public String getAdmin(@PathVariable String email, ModelMap modelMap, HttpServletRequest request) {
        AdminProfileInformation adminProfileInformation = adminProfileInformationService.getAdmin(email);
        modelMap.addAttribute("adminInformation", adminProfileInformation);
        String email1 = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email1);
        return "/Admin/AdminHomePage";
    }

    @RequestMapping("/{email}/admin/all-users/admin/{adminEmail}")
    public String getAdminForAdmin(@PathVariable String adminEmail, ModelMap modelMap, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        AdminProfileInformation admin = adminProfileInformationService.getAdmin(adminEmail);
        modelMap.addAttribute("adminInformation", admin);
        return "/Admin/ViewAdminUserProfile";
    }

    @RequestMapping("/signUp/admin")
    public String adminCreationForm(HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        return "/Creating account/CreatingAdminProfile";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signUp/{email}/admin")
    public String addAdmin(AdminProfileInformation adminProfileInformation, HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        adminProfileInformation.setEmail(email);
        adminProfileInformationService.addAdmin(adminProfileInformation);
        return "redirect:/" + email + "/admin/feedbacks";
    }

    @RequestMapping("/admin/profile/edit")
    public String updateAdminForm(HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email);
        AdminProfileInformation admin = adminProfileInformationService.getAdmin(email);
        modelMap.addAttribute("adminInformation", admin);
        return "/Admin/EditingAdminProfile";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{email}/admin/profile/edit")
    public String updateAdmin(AdminProfileInformation adminProfileInformation, @PathVariable String email, ModelMap modelMap, HttpServletRequest request) {
        adminProfileInformation.setEmail(email);
        String email1 = (String) request.getSession().getAttribute("email");
        modelMap.addAttribute("email", email1);
        adminProfileInformationService.updateAdmin(adminProfileInformation);
        return "redirect:/" + email + "/admin/profile";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{email}/admin/view-admins/{adminEmail}/edit")
    public void updateAdminFromAdmin(@RequestBody AdminProfileInformation adminProfileInformation) {
        adminProfileInformationService.updateAdmin(adminProfileInformation);
    }

   /* @RequestMapping(method = RequestMethod.DELETE, value = "/{email}/admin/profile/delete")
    public void deleteAdmin(@PathVariable String email) {
        adminProfileInformationService.deleteAdmin(email);
    }*/

    @RequestMapping(method = RequestMethod.POST, value = "/{email}/admin/all-users/admin/{adminEmail}/delete")
    public String deleteAdminFromAdmin(@PathVariable String adminEmail, HttpServletRequest request, ModelMap modelMap ) {
        String email = (String) request.getSession().getAttribute("email");
        adminProfileInformationService.deleteAdmin(adminEmail);
        loginInformationService.deleteUser(adminEmail);
        modelMap.addAttribute("email", email);
        return "redirect:/" + email + "/admin/all-users";
    }
}
