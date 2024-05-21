package com.example.yallah_project.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class User  implements Parcelable {

    @PrimaryKey
    @NonNull
    private UUID id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "age")
    private String  age;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "UserPicture")
    private byte[] profilePicture;

     @ColumnInfo(name = "role")
    private UserRole role;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "UserIdentity")
    private byte[]  IdentityPicture;

@ColumnInfo(name = "governmentIdType")
    GovernmentIdType governmentIdType;

    public User() {
        this.id = UUID.randomUUID();

    }

    public User(String email, String name, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }


    @NonNull
    public UUID getId() {
        return id;
    }

    public void setId(@NonNull UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public byte[] getIdentityPicture() {
        return IdentityPicture;
    }

    public void setIdentityPicture(byte[] identityPicture) {
        IdentityPicture = identityPicture;
    }

    public GovernmentIdType getGovernmentIdType() {
        return governmentIdType;
    }

    public void setGovernmentIdType(GovernmentIdType governmentIdType) {
        this.governmentIdType = governmentIdType;
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", profilePicture=" + Arrays.toString(profilePicture) +
                ", role=" + role +
                '}';
    }

    protected User(Parcel in) {
        id = (UUID) in.readSerializable();
        name = in.readString();
        email = in.readString();
        password = in.readString();
        long tmpAge = in.readLong();
        age = in.readString();
        profilePicture = in.createByteArray();
        role = (UserRole) in.readSerializable();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(id);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(age);
         parcel.writeByteArray(profilePicture);
        parcel.writeSerializable(role);
        parcel.writeByteArray(IdentityPicture);
    }



}


