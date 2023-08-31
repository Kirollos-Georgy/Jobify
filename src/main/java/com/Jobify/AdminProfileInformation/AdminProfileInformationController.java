package com.Jobify.AdminProfileInformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminProfileInformationController {

    @Autowired
    private AdminProfileInformationService adminProfileInformationService;

    @RequestMapping("/jobify/{email}/admin/view-admins")
    public List<AdminProfileInformation> getAllAdmins() {
        return adminProfileInformationService.getAllAdmins();
    }

    @RequestMapping("/jobify/{email}/admin/profile")
    public AdminProfileInformation getAdmin(@PathVariable String email) {
        return adminProfileInformationService.getAdmin(email);
    }

    @RequestMapping("/jobify/{email}/admin/view-admins/{adminEmail}")
    public AdminProfileInformation getAdminForAdmin(@PathVariable String adminEmail) {
        return adminProfileInformationService.getAdmin(adminEmail);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/jobify/signUp/admin")
    public void addAdmin(@RequestBody AdminProfileInformation adminProfileInformation) {
        adminProfileInformationService.addAdmin(adminProfileInformation);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/jobify/{email}/admin/profile/edit")
    public void updateAdmin(@RequestBody AdminProfileInformation adminProfileInformation) {
        adminProfileInformationService.updateAdmin(adminProfileInformation);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/jobify/{email}/admin/view-admins/{adminEmail}/edit")
    public void updateAdminFromAdmin(@RequestBody AdminProfileInformation adminProfileInformation) {
        adminProfileInformationService.updateAdmin(adminProfileInformation);
    }

   /* @RequestMapping(method = RequestMethod.DELETE, value = "/jobify/{email}/admin/profile/delete")
    public void deleteAdmin(@PathVariable String email) {
        adminProfileInformationService.deleteAdmin(email);
    }*/

    @RequestMapping(method = RequestMethod.DELETE, value = "/jobify/{email}/admin/view-admins/{adminEmail}/delete")
    public void deleteAdminFromAdmin(@PathVariable String adminEmail) {
        adminProfileInformationService.deleteAdmin(adminEmail);
    }
}
