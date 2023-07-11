package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;


@Configuration
public class RabbitMQConfig {

    private static final String EXCHANGE_NAME = "login.exchange";
    private static final String QUEUE_NAME = "login.queue";
    private static final String ROUTING_KEY = ""; // 根据需求设置路由键

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding binding(DirectExchange directExchange, Queue queue) {
        return BindingBuilder.bind(queue).to(directExchange).with(ROUTING_KEY);
    }

//    @Bean
//    public LoginResultPublisher loginResultPublisher(AmqpTemplate amqpTemplate, LoginService loginService) {
//        return new LoginResultPublisher(amqpTemplate, loginService);
//    }
}
