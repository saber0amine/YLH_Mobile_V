package com.example.yallah_project.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {
    private String street;
    private String formattedAddress; // Human-readable address obtained from map API

    private String city;
    private String state;
    private String country;
    private int zipCode;
    private Double latitude;
    private Double longitude;
    private Activity activity  ;

    public Location()  {
    }

    public Location(String street, String city, String state, String country, int zipCode, Double latitude, Double longitude , Activity activity) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.activity  =activity  ;
    }

    public Location(String formattedAddress  ,double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.formattedAddress = formattedAddress;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    protected Location(Parcel in) {
        street = in.readString();
        city = in.readString();
        state = in.readString();
        country = in.readString();
        zipCode = in.readInt();
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(street);
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
