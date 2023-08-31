package com.Jobify.JobPostings;

import com.Jobify.Applications.Applications;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobPostingsRepository extends CrudRepository<JobPostings, String> {
    public List<JobPostings> findByEmail(String email);
    public List<JobPostings> findByStatus(String status);
    public List<JobPostings> findByEmailAndStatus(String email, String status);
}
