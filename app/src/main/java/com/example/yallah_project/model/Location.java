package com.example.yallah_project.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.PublicKey;
import java.util.jar.Pack200;

public class Location  implements Parcelable {
    private String street;
    private String city;
    private String state;
    private String country;
    private int zipCode;

public Location() {
    }
    public  Location(String street, String city, String state, String country, int zipCode){
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }

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



    protected Location(Parcel in) {
        city = in.readString();
        state = in.readString();
        country = in.readString();
        zipCode = in.readInt();
        latitude = in.readByte() == 0 ? null : in.readDouble();
        longitude = in.readByte() == 0 ? null : in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(country);
        dest.writeInt(zipCode);
        if (latitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latitude);
        }
        if (longitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(longitude);
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
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
