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



    private List<String> ActivityImages;


    private ActivityCategorie activityCategorie;
    private int MinAge;
private int MaxAge;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOrganisteur() {
        return organisteur;
    }

    public void setOrganisteur(User organisteur) {
        this.organisteur = organisteur;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getDateOfPublish() {
        return dateOfPublish;
    }

    public void setDateOfPublish(LocalDateTime dateOfPublish) {
        this.dateOfPublish = dateOfPublish;
    }


    public LocalDateTime getDateOfStart() {
    return dateOfStart;
}

public void setDateOfStart(LocalDateTime dateOfStart) {
    this.dateOfStart = dateOfStart;
}

public LocalDateTime getDateOfEnd() {
    return dateOfEnd;
}

public void setDateOfEnd(LocalDateTime dateOfEnd) {
    this.dateOfEnd = dateOfEnd;
}

public Instant getDuration() {
    return duration;
}

public void setDuration(Instant duration) {
    this.duration = duration;
}

public Long getPrice() {
    return price;
}

public void setPrice(Long price) {
    this.price = price;
}

public int getCapacity() {
    return capacity;
}

public void setCapacity(int capacity) {
    this.capacity = capacity;
}

public List<String> getActivityImages() {
    return ActivityImages;
}

public void setActivityImages(List<String> activityImages) {
    ActivityImages = activityImages;
}

public ActivityCategorie getActivityCategorie() {
    return activityCategorie;
}

public void setActivityCategorie(ActivityCategorie activityCategorie) {
    this.activityCategorie = activityCategorie;
}

public int getMinAge() {
    return MinAge;
}

public void setMinAge(int age) {
    this.MinAge = age;
}

    public int getMaxAge() {
        return MaxAge;
    }

    public void setMaxAge(int maxAge) {
        MaxAge = maxAge;
    }

    public ActivityStatus getStatus() {
    return status;
}

public void setStatus(ActivityStatus status) {
    this.status = status;
}
}
