package com.Jobify.Applications;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApplicationsRepository extends CrudRepository<Applications, String> {
    List<Applications> findByJobPostingId(Long jobPostingId);
    List<Applications> findByStatus(String status);
    List<Applications> findByJobPostingIdAndStatus(long jobPostingId, String status);
    List<Applications> findByStudentEmail(String studentEmail);
    List<Applications> findByStudentEmailAndStatus(String email, String status);

    Applications findByStudentEmailAndJobPostingId(String email, long jobPostingId);
}
