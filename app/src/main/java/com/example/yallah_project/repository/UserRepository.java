package com.example.yallah_project.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.yallah_project.dao.UserDao;
import com.example.yallah_project.database.AppDatabase;
import com.example.yallah_project.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private UserDao userDao;
     private final ExecutorService executorService;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        executorService = Executors.newSingleThreadExecutor();
    }


    public void insert(User user) {
        Log.i("Debuggin" , "Repo : before saving ") ;

        executorService.execute(() -> {
            userDao.insert(user);
        });
    }

   // UserRepository.java
public LiveData<User> getUserByEmailAndPassword(String email, String password) {
    return userDao.getUserByEmailAndPassword(email, password);
}


    public void update(User user) {
        executorService.execute(() -> {
            userDao.update(user);
        });
    }


    public LiveData<User> getUserByEmail(String userEmail) {
        return userDao.getUserByEmail(userEmail);
    }
}