package com.example.yallah_project.dtos;

import com.example.yallah_project.model.ActivityCategorie;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.UUID;

public class OrgnizerActivitiesDto {

    @SerializedName("totalOrginzerActivities")
    private Integer totalOrginzerActivities;

    @SerializedName("activitiesNameAndId")
    private HashMap<String, UUID> activitiesNameAndId;

    @SerializedName("activitiesTotalByCategory")
    HashMap<ActivityCategorie, Long>  activitiesTotalByCategory;
    @SerializedName("activitiesNameandBookedUser")
    private HashMap<String, Integer> activitiesNameandBookedUser;

    // Getters and Setters
    public Integer getTotalOrginzerActivities() {
        return totalOrginzerActivities;
    }

    public void setTotalOrginzerActivities(Integer totalOrginzerActivities) {
        this.totalOrginzerActivities = totalOrginzerActivities;
    }

    public HashMap<String, UUID> getActivitiesNameAndId() {
        return activitiesNameAndId;
    }

    public void setActivitiesNameAndId(HashMap<String, UUID> activitiesNameAndId) {
        this.activitiesNameAndId = activitiesNameAndId;
    }

    public HashMap<String, Integer> getActivitiesNameandBookedUser() {
        return activitiesNameandBookedUser;
    }


    public void setActivitiesNameandBookedUser(HashMap<String, Integer> activitiesNameandBookedUser) {
        this.activitiesNameandBookedUser = activitiesNameandBookedUser;
    }

    public HashMap<ActivityCategorie, Long> getActivitiesTotalByCategory() {
        return activitiesTotalByCategory;
    }

    public void setActivitiesTotalByCategory(HashMap<ActivityCategorie, Long> activitiesTotalByCategory) {
        this.activitiesTotalByCategory = activitiesTotalByCategory;
    }
}
