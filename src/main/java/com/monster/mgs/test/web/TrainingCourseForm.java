package com.monster.mgs.test.web;

import com.monster.mgs.test.model.TrainingCourse;
import com.monster.mgs.test.model.TrainingCourseSection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TrainingCourseForm {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private TrainingCourse trainingCourse;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date trainingCourseDate;
    
    private TrainingCourseSection favorite;
    private Byte rate;
    private String comment;

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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public TrainingCourse getTrainingCourse() {
        return trainingCourse;
    }

    public void setTrainingCourse(TrainingCourse trainingCourse) {
        this.trainingCourse = trainingCourse;
    }

    public Date getTrainingCourseDate() {
        return trainingCourseDate;
    }

    public void setTrainingCourseDate(Date trainingCourseDate) {
        this.trainingCourseDate = trainingCourseDate;
    }

    public TrainingCourseSection getFavorite() {
        return favorite;
    }

    public void setFavorite(TrainingCourseSection favorite) {
        this.favorite = favorite;
    }

    public Byte getRate() {
        return rate;
    }

    public void setRate(Byte rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
