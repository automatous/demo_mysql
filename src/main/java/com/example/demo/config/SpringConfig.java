package com.example.demo.config;

import com.example.demo.dao.StudentDao;
import com.example.demo.service.StudentService;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public StudentService studentService() {
        StudentService studentService = new StudentService();
        StudentDao studentDao = new StudentDao();
        studentService.setStudentDao(studentDao);
        return studentService;
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}