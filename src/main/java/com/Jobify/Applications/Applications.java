package com.Jobify.Applications;

import com.Jobify.BlobSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

@Entity
public class Applications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long jobPostingId;
    private String studentEmail;
    private String status;
    @Lob
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JsonSerialize(using = BlobSerializer.class)
    private Blob resume;
    @Lob
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JsonSerialize(using = BlobSerializer.class)
    private Blob coverLetter;
    @Lob
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JsonSerialize(using = BlobSerializer.class)
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

    public String getResumeBase64() {
        try {
            byte[] transcriptData = resume.getBytes(1, (int) resume.length());
            return Base64.getEncoder().encodeToString(transcriptData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCoverLetterBase64() {
        try {
            byte[] transcriptData = coverLetter.getBytes(1, (int) coverLetter.length());
            return Base64.getEncoder().encodeToString(transcriptData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTranscriptBase64() {
        try {
            byte[] transcriptData = unofficialTranscript.getBytes(1, (int) unofficialTranscript.length());
            return Base64.getEncoder().encodeToString(transcriptData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}