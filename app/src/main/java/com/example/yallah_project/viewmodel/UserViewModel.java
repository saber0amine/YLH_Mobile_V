package com.example.yallah_project.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.yallah_project.model.User;
import com.example.yallah_project.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<List<User>> allUsers;

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
        allUsers = userRepository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void insert(User user) {
        userRepository.insert(user);
    }

// UserViewModel.java
public LiveData<User> getUserByEmailAndPassword(String email, String password) {
    return userRepository.getUserByEmailAndPassword(email, password);
}


    public void update(User user) {
        userRepository.update(user);
    }
}