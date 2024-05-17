package com.example.yallah_project.dtos;




import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.yallah_project.model.ActivityCategorie;
import com.example.yallah_project.model.Location;
import com.google.gson.annotations.SerializedName;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


 public class ActivityDto implements Parcelable {

     @SerializedName("id")
     private UUID id;

     @SerializedName("name")
     private String name;

     @SerializedName("description")
     private String description;

     @SerializedName("organizerId")
     private UUID organizerId;
     @SerializedName("organizerName")
     private String organizerName;
     @SerializedName("location")
     private Location location;

    @SerializedName("dateOfPublish")
     private  List<String>  dateOfPublish;

     @SerializedName("dateOfStart")
     private List<String> dateOfStart;

     @SerializedName("dateOfEnd")
     private  List<String>  dateOfEnd;

     @SerializedName("duration")
     private  int duration;

     @SerializedName("price")
     private Long price;

     @SerializedName("capacity")
     private int capacity;

     @SerializedName("activityImages")
     private List<String> ActivityImages;

     @SerializedName("activityCategorie")
     private ActivityCategorie activityCategorie;

     @SerializedName("Maxage")
     private int Maxage;

     @SerializedName("Minage")
     private int Minage;




    public ActivityDto(UUID id, String name, String description, String organizerName, UUID organizerId, Location location,  List<String>  dateOfPublish,  List<String>  dateOfStart,  List<String>  dateOfEnd, String duration, Long price, int capacity, List<String> activityImages, ActivityCategorie activityCategorie, int maxage, int minage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.organizerName = organizerName;
        this.organizerId = organizerId;
        this.location = location;
         this.dateOfPublish  = dateOfPublish ;
            this.dateOfStart  = dateOfStart ;
            this.dateOfEnd  = dateOfEnd ;
        this.price = price;
        this.capacity = capacity;
        ActivityImages = activityImages;
        this.activityCategorie = activityCategorie;
        Maxage = maxage;
        Minage = minage;
    }




     public UUID getId() {
        return id;
    }
public void setId(UUID id) {
    this.id = id;
}

public UUID getOrganizerId() {
    return organizerId;
}

public void setOrganizerId(UUID organizerId) {
    this.organizerId = organizerId;
}

public void setOrganizerName(String organizerName) {
    this.organizerName = organizerName;
}

public Location getLocation() {
    return location;
}

public void setLocation(Location location) {
    this.location = location;
}

     public  List<String>  getDateOfStart() {
         return dateOfStart;
     }

     public  List<String>  getDateOfEnd() {
         return dateOfEnd;
     }

     public  int  getDuration() {
         return duration;
     }

     public void setDuration( int  duration) {
         this.duration = duration;
     }

     public void setDateOfEnd( List<String>  dateOfEnd) {
         this.dateOfEnd = dateOfEnd;
     }

     public void setDateOfStart( List<String>  dateOfStart) {
         this.dateOfStart = dateOfStart;
     }

     public  List<String>  getDateOfPublish() {
         return dateOfPublish;
     }

     public void setDateOfPublish( List<String>  dateOfPublish) {
         this.dateOfPublish = dateOfPublish;
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

public int getMaxage() {
    return Maxage;
}

public void setMaxage(int maxage) {
    Maxage = maxage;
}

public int getMinage() {
    return Minage;
}

public void setMinage(int minage) {
    Minage = minage;
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

        public String getOrganizerName() {
            return organizerName;
        }


     protected ActivityDto(Parcel in) {
         id = in.readByte() == 0 ? null : (UUID) in.readSerializable();
         name = in.readByte() == 0 ? null : in.readString();
         description = in.readByte() == 0 ? null : in.readString();
         organizerName = in.readByte() == 0 ? null : in.readString();
         organizerId = in.readByte() == 0 ? null : (UUID) in.readSerializable();
         if (in.readByte() == 0) {
             location = null;
         } else {
             location = in.readParcelable(Location.class.getClassLoader());
         }         dateOfPublish = in.readByte() == 0 ? null : in.createStringArrayList();
         dateOfStart = in.readByte() == 0 ? null : in.createStringArrayList();
         dateOfEnd = in.readByte() == 0 ? null : in.createStringArrayList();
         duration = in.readInt();
         price = in.readByte() == 0 ? null : in.readLong();
         capacity = in.readInt();
         ActivityImages = in.readByte() == 0 ? null : in.createStringArrayList();
         activityCategorie = ActivityCategorie.values()[in.readInt()];
         Maxage = in.readInt();
         Minage = in.readInt();
     }

     @Override
     public void writeToParcel(@NonNull Parcel dest, int flags) {
         dest.writeByte(id == null ? (byte) 0 : (byte) 1);
         if (id != null) {
             dest.writeSerializable(id);
         }
         dest.writeByte(name == null ? (byte) 0 : (byte) 1);
         if (name != null) {
             dest.writeString(name);
         }
         dest.writeByte(description == null ? (byte) 0 : (byte) 1);
         if (description != null) {
             dest.writeString(description);
         }
         dest.writeByte(organizerName == null ? (byte) 0 : (byte) 1);
         if (organizerName != null) {
             dest.writeString(organizerName);
         }
         dest.writeByte(organizerId == null ? (byte) 0 : (byte) 1);
         if (organizerId != null) {
             dest.writeSerializable(organizerId);
         }
         if (location == null) {
             dest.writeByte((byte) 0);
         } else {
             dest.writeByte((byte) 1);
             dest.writeParcelable(location, flags);
         }
         dest.writeByte(dateOfPublish == null ? (byte) 0 : (byte) 1);
         if (dateOfPublish != null) {
             dest.writeStringList(dateOfPublish);
         }
         dest.writeByte(dateOfStart == null ? (byte) 0 : (byte) 1);
         if (dateOfStart != null) {
             dest.writeStringList(dateOfStart);
         }
         dest.writeByte(dateOfEnd == null ? (byte) 0 : (byte) 1);
         if (dateOfEnd != null) {
             dest.writeStringList(dateOfEnd);
         }
         dest.writeInt(duration);
         dest.writeByte(price == null ? (byte) 0 : (byte) 1);
         if (price != null) {
             dest.writeLong(price);
         }
         dest.writeInt(capacity);
         dest.writeByte(ActivityImages == null ? (byte) 0 : (byte) 1);
         if (ActivityImages != null) {
             dest.writeStringList(ActivityImages);
         }
         dest.writeInt(activityCategorie.ordinal());
         dest.writeInt(Maxage);
         dest.writeInt(Minage);
     }
        public static final Creator<ActivityDto> CREATOR = new Creator<ActivityDto>() {
            @Override
            public ActivityDto createFromParcel(Parcel in) {
                return new ActivityDto(in);
            }

            @Override
            public ActivityDto[] newArray(int size) {
                return new ActivityDto[size];
            }
        };
     @Override
     public int describeContents() {
         return 0;
     }

 }
