package com.Jobify.Feedback;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Feedback {
    @Id
    private String email;

    @Column(columnDefinition = "TEXT")
    private String subject;
    private int rating;

    public Feedback() {
        email = "";
        subject = "";
        rating = -1;
    }

    public Feedback(String email, String subject, int rating) {
        this.email = email;
        this.subject = subject;
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
