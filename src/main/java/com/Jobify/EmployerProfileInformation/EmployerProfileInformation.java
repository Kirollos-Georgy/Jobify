package com.Jobify.EmployerProfileInformation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;

@Entity
public class EmployerProfileInformation {
    @Id
    private String email;
    private String firstName;
    private String lastName;
    private String company;

    public EmployerProfileInformation() {
        email = "";
        firstName = "";
        lastName = "";
        company = "";
    }

    public EmployerProfileInformation(String email, String firstName, String lastName, String company) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
