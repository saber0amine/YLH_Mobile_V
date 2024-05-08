package com.example.yallah_project.model;

public class Location {
    private String street;
    private String city;
    private String state;
    private String country;
    private int zipCode;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // I will use them later for the Googlemap or OpenStreetMap APIs
    //---->
    // Calculating distances between locations
    // Searching for activities near a location:
    //--->
    private Double latitude;
    private Double longitude;
}
/*
let map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: latitude, lng: longitude },
zoom: 8,
        });
        new google.maps.Marker({
    position: { lat: latitude, lng: longitude },
    map: map,
}); */
