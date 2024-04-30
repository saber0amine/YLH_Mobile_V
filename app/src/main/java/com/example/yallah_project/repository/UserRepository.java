package com.example.yallah_project.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.yallah_project.dao.UserDao;
import com.example.yallah_project.database.AppDatabase;
import com.example.yallah_project.model.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;
    private final ExecutorService executorService;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        allUsers = userDao.getAll();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
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

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao asyncTaskDao;

        insertAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }











}