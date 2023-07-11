package com.example.demo.model;

public class LoginResultMessage {
    private String username;
    private boolean success;
    
    
	public LoginResultMessage(String username, boolean success) {
		super();
		this.username = username;
		this.success = success;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}


}