package com.example.moncandidature.models;

import java.util.Date;

public class Candidature {

    String postName;
    String company;
    Date date_applied;
    Date date_interview;

    public Candidature(String pName, String cName, Date dApplied){
        postName = pName;
        company = cName;
        date_applied = dApplied;
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
}
