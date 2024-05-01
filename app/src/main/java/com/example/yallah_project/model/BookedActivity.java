package com.example.yallah_project.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "booked_activities",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "id",
                onDelete = ForeignKey.CASCADE))
public class BookedActivity {
    @PrimaryKey
    public long activityId;

    public long id;

    public String activityName;
}