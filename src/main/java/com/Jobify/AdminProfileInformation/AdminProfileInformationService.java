package com.Jobify.AdminProfileInformation;

import com.Jobify.EmployerProfileInformation.EmployerProfileInformation;
import com.Jobify.StudentProfileInformation.StudentProfileInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminProfileInformationService {

    @Autowired
    private AdminProfileInformationRepository adminProfileInformationRepository;
    public List<AdminProfileInformation> getAllAdmins() {
        List<AdminProfileInformation> adminProfileInformation = new ArrayList<>();
        adminProfileInformationRepository.findAll().forEach(adminProfileInformation::add);
        return adminProfileInformation;
    }
    public AdminProfileInformation getAdmin(String email) {
        return adminProfileInformationRepository.findById(email).get();
    }
    public void addAdmin(AdminProfileInformation adminProfileInformation) {
        adminProfileInformationRepository.save(adminProfileInformation);
    }

    public void updateAdmin(AdminProfileInformation adminProfileInformation) {
        adminProfileInformationRepository.save(adminProfileInformation);
    }

    public void deleteAdmin(String email) {
        adminProfileInformationRepository.deleteById(email);
    }
}
