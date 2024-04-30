package com.example.yallah_project.dao;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.yallah_project.model.User;

import java.util.List;
import java.util.UUID;

@Dao
public interface UserDao {
@Query("SELECT * FROM User")
LiveData<List<User>> getAll();
    @Query("SELECT * FROM User WHERE id = :id")
    User getById(UUID id);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

 @Query("SELECT * FROM User WHERE email = :email AND password = :password LIMIT 1")
LiveData<User> getUserByEmailAndPassword(String email, String password);
}