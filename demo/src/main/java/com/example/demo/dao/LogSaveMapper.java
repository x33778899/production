package com.example.demo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.example.demo.model.LogSave;
@Mapper
public interface LogSaveMapper {
	
    @Insert("INSERT INTO log (token,ip) VALUES (#{token},#{ip})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int SaveLoginStatus(LogSave logSave);
}
