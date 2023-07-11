package com.example.demo.service.serviceImple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.LogSaveMapper;
import com.example.demo.model.LogSave;
import com.example.demo.service.LogSaveService;

@Service
public class LogSaveServiceImple implements LogSaveService{
		
	private LogSaveMapper logSaveMapper;
	
	@Autowired
	public LogSaveServiceImple(LogSaveMapper logSaveMapper) {
		this.logSaveMapper = logSaveMapper;
	}
	
	@Override
	public int SaveLoginStatus(LogSave logSave) {
		// TODO Auto-generated method stub
		return logSaveMapper.SaveLoginStatus(logSave);
	}



}
