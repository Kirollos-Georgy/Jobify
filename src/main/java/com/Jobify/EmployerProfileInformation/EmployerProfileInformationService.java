package com.Jobify.EmployerProfileInformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployerProfileInformationService {

    @Autowired
    private EmployerProfileInformationRepository employerProfileInformationRepository;
    public List<EmployerProfileInformation> getAllEmployers() {
        List<EmployerProfileInformation> employerProfileInformation = new ArrayList<>();
        employerProfileInformationRepository.findAll().forEach(employerProfileInformation::add);
        return employerProfileInformation;
    }
    public EmployerProfileInformation getEmployer(String email) {
        return employerProfileInformationRepository.findById(email).get();
    }
    public void addEmployer(EmployerProfileInformation employerProfileInformation) {
        employerProfileInformationRepository.save(employerProfileInformation);
    }

    public void updateEmployer(EmployerProfileInformation employerProfileInformation) {
        employerProfileInformationRepository.save(employerProfileInformation);
    }

    public void deleteEmployer(String email) {
        employerProfileInformationRepository.deleteById(email);
    }
}
