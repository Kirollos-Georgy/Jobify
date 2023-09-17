package com.Jobify.JobPostings;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobPostingsRepository extends CrudRepository<JobPostings, String> {
    List<JobPostings> findByEmail(String email);
    List<JobPostings> findByStatus(String status);
    List<JobPostings> findByEmailAndStatus(String email, String status);

    List<JobPostings> findByTitleContaining(String search);
}
