package com.Jobify.Applications;

import jakarta.persistence.*;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;

@Entity
public class Applications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long jobPostingId;
    private String studentEmail;
    private String status;
    @Lob
    private Blob resume;
    @Lob
    private Blob coverLetter;
    @Lob
    private Blob unofficialTranscript;

    public Applications() {
        jobPostingId = (long) -1;
        studentEmail = "";
        status = "";
        try {
            this.resume = new SerialBlob(new byte[0]);
            this.coverLetter = new SerialBlob(new byte[0]);
            this.unofficialTranscript = new SerialBlob(new byte[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Applications(Long jobPostingsID, String studentEmail, String title, String status, Blob resume, Blob coverLetter, Blob unofficialTranscript) {
        this.jobPostingId = jobPostingsID;
        this.studentEmail = studentEmail;
        this.status = status;
        this.resume = resume;
        this.coverLetter = coverLetter;
        this.unofficialTranscript = unofficialTranscript;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobPostingsID() {
        return jobPostingId;
    }

    public void setJobPostingsID(Long jobPostingsID) {
        this.jobPostingId = jobPostingsID;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Blob getResume() {
        return resume;
    }

    public void setResume(Blob resume) {
        this.resume = resume;
    }

    public Blob getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(Blob coverLetter) {
        this.coverLetter = coverLetter;
    }

    public Blob getUnofficialTranscript() {
        return unofficialTranscript;
    }

    public void setUnofficialTranscript(Blob unofficialTranscript) {
        this.unofficialTranscript = unofficialTranscript;
    }
}