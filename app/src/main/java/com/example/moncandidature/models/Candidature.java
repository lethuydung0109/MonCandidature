package com.example.moncandidature.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Candidature  extends RealmObject {

    @PrimaryKey int application_id;
    public String postName;
    public String company;
    public Date date_applied;
    public Date date_interview;
    public Date date_accepted;
    public boolean rejected;
    public String link;
    public String comment;
    public Date date_reminder;

    public Candidature() {
    }

    public Candidature(int application_id, String postName, String company,String link, String comment,Date dApplied,Date dInterview, Date dReview) {
        this.application_id = application_id;
        this.postName = postName;
        this.company = company;
        this.link = link;
        this.comment = comment;
        date_applied = dApplied;
        date_interview = dInterview;
        date_accepted = dReview;
        this.rejected = false;

    }

    //getter

    public String getPostName() {
        return postName;
    }

    public String getCompany() {
        return company;
    }

    public Date getDate_applied() {
        return date_applied;
    }

    public Date getDate_interview() {
        return date_interview;
    }

    public int getApplication_id() {
        return application_id;
    }

    public String getLink() {
        return link;
    }

    public String getComment() {
        return comment;
    }

    public Date getDate_reminder() {
        return date_reminder;
    }

    //setter
    public void setPostName(String postName) {
        this.postName = postName;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDate_applied(Date date_applied) {
        this.date_applied = date_applied;
    }

    public void setDate_interview(Date date_interview) {
        this.date_interview = date_interview;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public void setApplication_id(int application_id) {
        this.application_id = application_id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate_reminder(Date date_reminder) {
        this.date_reminder = date_reminder;
    }

    public Date getDate_accepted() {
        return date_accepted;
    }

    public void setDate_accepted(Date date_accepted) {
        this.date_accepted = date_accepted;
    }


    @Override
    public String toString() {
        return "Candidature{" +
                "application_id=" + application_id +
                ", postName='" + postName + '\'' +
                ", company='" + company + '\'' +
                ", date_applied=" + date_applied +
                ", date_interview=" + date_interview +
                ", date_accepted=" + date_accepted +
                ", rejected=" + rejected +
                ", link='" + link + '\'' +
                ", comment='" + comment + '\'' +
                ", date_reminder=" + date_reminder +
                '}';
    }
}
