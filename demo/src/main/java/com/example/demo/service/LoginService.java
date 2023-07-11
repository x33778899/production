package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.config.LoginResultPublisher;
import com.example.demo.dao.UserMapper;
import com.example.demo.model.LoginResultMessage;
import com.example.demo.model.User;

@Service
public class LoginService {
    private final UserMapper userMapper; // 注入UserMapper
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    public LoginService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void recordLoginResult(LoginResultMessage message) {
        // 根据消息中的信息记录用户登录结果到数据库
        User user = userMapper.getUserByUsername(message.getUsername(), null);
        if (user != null) {
            user.setLastLoginResult(message.isSuccess());
//            userMapper.update(user);
            logger.info("Login result recorded for user: {}", user.getAccount());
        }
    }
}


