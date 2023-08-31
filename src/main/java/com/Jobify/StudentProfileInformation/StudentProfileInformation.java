package com.Jobify.StudentProfileInformation;

import com.Jobify.BlobSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

@Entity
public class
StudentProfileInformation {
    @Id
    private String email;
    private String firstName;
    private String lastName;
    private String fieldOfStudy;
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
    @Lob
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JsonSerialize(using = BlobSerializer.class)
    private Blob profilePicture;


    public StudentProfileInformation() {
        email = "";
        firstName = "";
        lastName = "";
        fieldOfStudy = "";
        try {
            this.resume = new SerialBlob(new byte[0]);
            this.coverLetter = new SerialBlob(new byte[0]);
            this.unofficialTranscript = new SerialBlob(new byte[0]);
            this.profilePicture = new SerialBlob(new byte[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public StudentProfileInformation(String email, String firstName, String lastName, String fieldOfStudy, Blob resume, Blob coverLetter, Blob unofficialTranscript, Blob profilePicture) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fieldOfStudy = fieldOfStudy;
        this.resume = resume;
        this.coverLetter = coverLetter;
        this.unofficialTranscript = unofficialTranscript;
        this.profilePicture = profilePicture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
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

    public Blob getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Blob profilePicture) {
        this.profilePicture = profilePicture;
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

    public String getProfilePictureBase64() {
        try {
            InputStream imageData = profilePicture.getBinaryStream();
            byte[] imageBytes = imageData.readAllBytes();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
