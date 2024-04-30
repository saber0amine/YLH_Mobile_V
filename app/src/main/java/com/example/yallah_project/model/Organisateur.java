package com.example.yallah_project.model;



import java.util.List;

 public class Organisateur extends User{
     List<Activity> activities ;

     private  GovernmentIdType governmentIdType  ;

    private String governmentIdCountry;

    private byte[] governmentIdFront;

     private byte[] governmentIdBack;

    //generate a constructor
    public Organisateur(String email, String name, String password, List<Activity> activities, GovernmentIdType governmentIdType, String governmentIdCountry, byte[] governmentIdFront, byte[] governmentIdBack) {
        super(email, name, password);
        this.activities = activities;
        this.governmentIdType = governmentIdType;
        this.governmentIdCountry = governmentIdCountry;
        this.governmentIdFront = governmentIdFront;
        this.governmentIdBack = governmentIdBack;
    }


    public Organisateur() {

    }
}