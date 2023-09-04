package com.Jobify.AdminProfileInformation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AdminProfileInformation {
    @Id
    private String email;
    private String firstName;
    private String lastName;
    private String adminRole;

    public AdminProfileInformation() {
        email = "";
        firstName = "";
        lastName = "";
        adminRole = "";
    }

    public AdminProfileInformation(String email, String firstName, String lastName, String adminRole) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.adminRole = adminRole;
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

    public String getAdminRole() {
        return adminRole;
    }

    public void setAdminRole(String adminRole) {
        this.adminRole = adminRole;
    }
}
