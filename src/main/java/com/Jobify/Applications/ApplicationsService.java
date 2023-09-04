package com.Jobify.Applications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationsService {

    @Autowired
    private ApplicationsRepository applicationsRepository;

    public List<Applications> getAllApplications() {
        List<Applications> applications = new ArrayList<>();
        applicationsRepository.findAll().forEach(applications::add);
        return applications;
    }
    public List<Applications> getAllApplicationsByJobPostingId(long jobPostingId) {
        List<Applications> applications = new ArrayList<>();
        applicationsRepository.findByJobPostingId(jobPostingId).forEach(applications::add);
        return applications;
    }
    public List<Applications> getAllApplicationsByStatus(String status) {
        List<Applications> applications = new ArrayList<>();
        applicationsRepository.findByStatus(status).forEach(applications::add);
        return applications;
    }
    public List<Applications> getAllApplicationsByJobPostingIdAndStatus(long jobPostingId, String status) {
        List<Applications> applications = new ArrayList<>();
        applicationsRepository.findByJobPostingIdAndStatus(jobPostingId, status).forEach(applications::add);
        return applications;
    }
    public List<Applications> getAllApplicationsByStudentEmail(String studentEmail) {
        List<Applications> applications = new ArrayList<>();
        applicationsRepository.findByStudentEmail(studentEmail).forEach(applications::add);
        return applications;
    }
    public List<Applications> getAllApplicationsByStudentEmailAndStatus(String studentEmail, String status) {
        List<Applications> applications = new ArrayList<>();
        applicationsRepository.findByStudentEmailAndStatus(studentEmail, status).forEach(applications::add);
        return applications;
    }
    public Applications getApplication(long id) {
        return applicationsRepository.findById(String.valueOf(id)).get();
    }
    public void addApplication(Applications application) {
        applicationsRepository.save(application);
    }

    public void updateApplication(Applications application) {
        applicationsRepository.save(application);
    }

    public void deleteApplication(long id) {
        applicationsRepository.deleteById(String.valueOf(id));
    }
}
