package com.Jobify.loginInformation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LoginInformation {
    @Id
    private String email;
    private String password;
    private String userType;

    public LoginInformation() {
        email = "";
        password = "";
        userType = "";
    }

    public LoginInformation(String email, String password, String userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
