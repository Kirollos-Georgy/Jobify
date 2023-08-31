package com.Jobify.Applications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApplicationsController {

    @Autowired
    private ApplicationsService applicationsService;

    @RequestMapping("/jobify/{email}/admin/applications")
    public List<Applications> getAllApplications() {
        return applicationsService.getAllApplications();
    }

    @RequestMapping("/jobify/{email}/student/applications")
    public List<Applications> getAllApplicationsByStudentEmail(@PathVariable String email) {
        return applicationsService.getAllApplicationsByStudentEmail(email);
        //go to view job postings /jobify/{email}/student/{jobPostingsID}
    }

    @RequestMapping("/jobify/{email}/employer/{jobPostingId}/applications")
    public List<Applications> getAllApplicationsByJobPostingId(@PathVariable long jobPostingId) {
        return applicationsService.getAllApplicationsByJobPostingId(jobPostingId);
    }

   /* @RequestMapping("/jobify/student/applications/{status}")
    public List<Applications> getAllApplicationsByStatus(@PathVariable String status) {
        return applicationsService.getAllApplicationsByStatus(status);
    }*/

    @RequestMapping("/jobify/{email}/employer/{jobPostingId}/applications/selected-for-interview")
    public List<Applications> getAllApplicationsByJobPostingIdAndStatus(@PathVariable long jobPostingId, @PathVariable String status) {
        return applicationsService.getAllApplicationsByJobPostingIdAndStatus(jobPostingId, status);
    }

    @RequestMapping("/jobify/{email}/student/interviews/selected-for-interview")
    public List<Applications> getAllApplicationsByStudentEmailAndStatus(@PathVariable String email) {
        return applicationsService.getAllApplicationsByStudentEmailAndStatus(email, "selected-for-interview");
        //go to view job postings /jobify/{email}/student/{jobPostingsID}
    }

    @RequestMapping("/jobify/{email}/student/applications/{id}")
    public Applications getApplicationStudent(@PathVariable long id) {
        return applicationsService.getApplication(id);
    }

    @RequestMapping("/jobify/{email}/student/applications/{status}/{id}")
    public Applications getApplicationStudentWithStatus(@PathVariable long id) {
        return applicationsService.getApplication(id);
    }

    @RequestMapping("/jobify/{email}/employer/{jobPostingId}/applications/{id}")
    public Applications getApplicationEmployer(@PathVariable long id) {
        return applicationsService.getApplication(id);
    }

    @RequestMapping("/jobify/{email}/employer/{jobPostingId}/applications/selected-for-interview/{id}")
    public Applications getApplicationEmployerWithStatus(@PathVariable long id) {
        return applicationsService.getApplication(id);
    }

    @RequestMapping("/jobify/{email}/admin/applications/{id}")
    public Applications getApplicationForAdmin(@PathVariable long id) {
        return applicationsService.getApplication(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/jobify/{email}/student/{jobPostingId}/apply")
    public void addApplication(@RequestBody Applications application) {
        applicationsService.addApplication(application);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/jobify/{email}/student/applications/{jobPostingId}/{id}/edit")
    public void updateApplication(@RequestBody Applications application) {
        applicationsService.updateApplication(application);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/jobify/{email}/student/applications/{jobPostingId}/{id}/delete")
    public void deleteApplication(@PathVariable long id) {
        applicationsService.deleteApplication(id);
    }
}
