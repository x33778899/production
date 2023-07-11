package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
    User getUserById(int id);
    User getUserByUsername(String account,String password);
    int registerUser(User user);
}
