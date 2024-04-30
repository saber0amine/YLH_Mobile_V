package com.example.yallah_project.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Activity {

    private UUID id ;
    private String name;
    private String description;

    private User organisteur ;


    private Location location;


    private LocalDateTime dateOfPublish;


    private LocalDateTime dateOfStart;

    private LocalDateTime dateOfEnd;

    private Instant duration ;

    private Long price;
    private int capacity;

    private List<byte[]> ActivityImages;


    private ActivityCategorie activityCategorie;
    private int age;

    // ACCEPTED OR REJECTED , PENDING FROM THE ADMIN
     private ActivityStatus status ;



    public Activity() {

    }



    //All getter and setter

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





























}
