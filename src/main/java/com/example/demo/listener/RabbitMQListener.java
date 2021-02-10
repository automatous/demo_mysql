package com.example.demo.listener;

import com.example.demo.bean.Student;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {

    @RabbitListener(queues = {"yanqun.infos"})
    public void invokeService(Student student) {
        System.out.println(student);
    }
}
