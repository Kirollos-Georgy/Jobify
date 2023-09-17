package com.Jobify.JobPostings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobPostingsService {

    @Autowired
    private JobPostingsRepository jobPostingsRepository;

    public List<JobPostings> getAllJobPostings() {
        List<JobPostings> jobPostings = new ArrayList<>();
        jobPostingsRepository.findAll().forEach(jobPostings::add);
        return jobPostings;
    }
    public List<JobPostings> getAllJobPostingsByEmployer(String email) {
        List<JobPostings> jobPostings = new ArrayList<>();
        jobPostingsRepository.findByEmail(email).forEach(jobPostings::add);
        return jobPostings;
    }

    public List<JobPostings> getAllJobPostingsByStatus(String status) {
        List<JobPostings> jobPostings = new ArrayList<>();
        jobPostingsRepository.findByStatus(status).forEach(jobPostings::add);
        return jobPostings;
    }

    public List<JobPostings> getAllJobPostingsByEmployerAndStatus(String email, String status) {
        List<JobPostings> jobPostings = new ArrayList<>();
        jobPostingsRepository.findByEmailAndStatus(email, status).forEach(jobPostings::add);
        return jobPostings;
    }

    public List<JobPostings> getJobPostingsByTitleSearch(String search) {
        List<JobPostings> jobPostings = new ArrayList<>();
        jobPostingsRepository.findByTitleContaining(search).forEach(jobPostings::add);
        return jobPostings;
    }

    public JobPostings getJobPosting(long id) {
        return jobPostingsRepository.findById(String.valueOf(id)).get();
    }

    public void addJobPosting(JobPostings jobPosting) {
        jobPostingsRepository.save(jobPosting);
    }

    public void updateJobPosting(JobPostings jobPosting) {
        jobPostingsRepository.save(jobPosting);
    }

    public void deleteJobPosting(long id) {
        jobPostingsRepository.deleteById(String.valueOf(id));
    }
}
