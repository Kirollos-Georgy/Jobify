package com.Jobify.Applications;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApplicationsRepository extends CrudRepository<Applications, String> {
    public List<Applications> findByJobPostingId(Long jobPostingId);
    public List<Applications> findByStatus(String status);
    public List<Applications> findByJobPostingIdAndStatus(long jobPostingId, String status);
    public List<Applications> findByStudentEmail(String studentEmail);
    public List<Applications> findByStudentEmailAndStatus(String email, String status);
}
