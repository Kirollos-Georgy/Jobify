package com.Jobify.AdminProfileInformation;

import com.Jobify.loginInformation.LoginInformationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminProfileInformationController {

    private final AdminProfileInformationService adminProfileInformationService;
    private final LoginInformationService loginInformationService;

    public AdminProfileInformationController(AdminProfileInformationService adminProfileInformationService, LoginInformationService loginInformationService) {
        this.adminProfileInformationService = adminProfileInformationService;
        this.loginInformationService = loginInformationService;
    }

    @RequestMapping("/admin/profile")
    public String getAdmin(ModelMap modelMap, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        AdminProfileInformation adminProfileInformation = adminProfileInformationService.getAdmin(email);
        modelMap.addAttribute("adminInformation", adminProfileInformation);
        return "/Admin/AdminHomePage";
    }

    @RequestMapping("/admin/all-users/admin/{adminEmail}")
    public String getAdminForAdmin(@PathVariable String adminEmail, ModelMap modelMap) {
        AdminProfileInformation admin = adminProfileInformationService.getAdmin(adminEmail);
        modelMap.addAttribute("adminInformation", admin);
        return "/Admin/ViewAdminUserProfile";
    }

    @RequestMapping("/signUp/admin")
    public String adminCreationForm() {
        return "/Creating account/CreatingAdminProfile";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signUp/admin")
    public String addAdmin(AdminProfileInformation adminProfileInformation, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        adminProfileInformation.setEmail(email);
        adminProfileInformationService.addAdmin(adminProfileInformation);
        return "redirect:/admin/feedbacks";
    }

    @RequestMapping("/admin/profile/edit")
    public String updateAdminForm(HttpServletRequest request, ModelMap modelMap) {
        String email = (String) request.getSession().getAttribute("email");
        AdminProfileInformation admin = adminProfileInformationService.getAdmin(email);
        modelMap.addAttribute("adminInformation", admin);
        return "/Admin/EditingAdminProfile";
    }

    @RequestMapping("/admin/all-users/admin/{adminEmail}/edit")
    public String updateAdminFormFromAnotherAdmin(ModelMap modelMap, @PathVariable String adminEmail) {
        AdminProfileInformation admin = adminProfileInformationService.getAdmin(adminEmail);
        modelMap.addAttribute("adminInformation", admin);
        return "/Admin/EditingAdminProfileFromAdmin";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/profile/edit")
    public String updateAdmin(AdminProfileInformation adminProfileInformation, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        adminProfileInformation.setEmail(email);
        adminProfileInformationService.updateAdmin(adminProfileInformation);
        return "redirect:/admin/profile";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/all-users/admin/{adminEmail}/edit")
    public String updateAdminFromAnotherAdmin(@PathVariable String adminEmail, AdminProfileInformation adminProfileInformation) {
        adminProfileInformation.setEmail(adminEmail);
        adminProfileInformationService.updateAdmin(adminProfileInformation);
        return "redirect:/admin/all-users/admin/" + adminEmail;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/all-users/admin/{adminEmail}/delete")
    public String deleteAdminFromAdmin(@PathVariable String adminEmail) {
        adminProfileInformationService.deleteAdmin(adminEmail);
        loginInformationService.deleteUser(adminEmail);
        return "redirect:/admin/all-users";
    }
}