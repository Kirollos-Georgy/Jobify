package com.Jobify.JobPostings;

import jakarta.persistence.*;

@Entity
public class JobPostings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String status;
    private String salary;
    private String deadline;
    private String jobLocation;

    private String company;

    public JobPostings() {
        email = "";
        title = "";
        description = "";
        status = "";
        salary = "";
        deadline = "";
        jobLocation = "";
        company = "";
    }

    public JobPostings(Long id, String email, String title, String description, String status, String salary, String deadline, String jobLocation, String company) {
        this.id = id;
        this.email = email;
        this.title = title;
        this.description = description;
        this.status = status;
        this.salary = salary;
        this.deadline = deadline;
        this.jobLocation = jobLocation;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
