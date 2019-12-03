package com.example.moncandidature.models;

import java.util.Date;

public class Candidature {

    String postName;
    String company;
    Date date_applied;
    Date date_interview;
    Date date_accepted;
    boolean rejected;

    public Candidature(String pName, String cName, Date dApplied){
        postName = pName;
        company = cName;
        date_applied = dApplied;
        date_interview = null;
        date_accepted = null;
        rejected = false;
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


    public Date getDate_accepted() {
        return date_accepted;
    }

    public void setDate_accepted(Date date_accepted) {
        this.date_accepted = date_accepted;
    }

}
