package com.example.demo.Handler;

public class ErrorResponse {
    private int status;
    private String loginStatus;
    private String message;
    
    public ErrorResponse(int status, String loginStatus, String message) {
        this.status = status;
        this.loginStatus = loginStatus;
        this.message = message;
    }
    
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

    
}
