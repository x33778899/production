package com.example.demo.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.example.demo.Handler.ErrorResponse;
import com.example.demo.model.LoginResultMessage;
import com.example.demo.model.User;
import com.example.demo.service.LoginService;

@Component
public class LoginResultPublisher {

    private final AmqpTemplate amqpTemplate;
    private final LoginService loginService;

    public LoginResultPublisher(AmqpTemplate amqpTemplate, LoginService loginService) {
        this.amqpTemplate = amqpTemplate;
        this.loginService = loginService;
    }

    // 在這裡添加發布登入結果的方法
    public void publishLoginResult(User user) {
        // 構建成功登入的回應
        ErrorResponse successResponse = new ErrorResponse(HttpStatus.OK.value(), "登入成功", user.getToken());
        // 將登入結果轉換成字串
        String messagePayload = successResponse.toString();
        // 發布訊息到交換器
        amqpTemplate.convertAndSend("login.exchange", "", messagePayload);
    }
}