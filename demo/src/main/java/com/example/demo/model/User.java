package com.example.demo.model;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 5513085521709019997L;
    private int id;
    private String account;
    private String password;
    private String role;
    private String token;
    private boolean lastLoginResult;

    public User() {
    }

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public User(int id, String account, String password) {
        this.id = id;
        this.account = account;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isLastLoginResult() {
        return lastLoginResult;
    }

    public void setLastLoginResult(boolean lastLoginResult) {
        this.lastLoginResult = lastLoginResult;
    }
}