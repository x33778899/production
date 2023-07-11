package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.LogSave;

public interface LogSaveService{
	int SaveLoginStatus(LogSave logSave);
}
