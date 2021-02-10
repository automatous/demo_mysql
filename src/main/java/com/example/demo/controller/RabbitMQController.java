package com.example.demo.controller;

import com.example.demo.bean.Student;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class RabbitMQController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    @RequestMapping("/testSendMQ")
    public String testSendDirectMQ(String name) {
        List<String> profession = Arrays.asList("developer", "teacher", "writer");
        rabbitTemplate.convertAndSend("myexchange.direct", "myqueues.infos", profession);
        return name;
    }

    @RequestMapping("/testReceiveMQ")
    public List<String> testReceiveDirectMQ() {
        List<String> profession = (List<String>) rabbitTemplate.receiveAndConvert("myqueues.infos");
        return profession;
    }

    @RequestMapping("/testListener")
    public Student testMQListener(Student student) {
        rabbitTemplate.convertAndSend("myexchange.direct", "yanqun.infos", student);
        return student;
    }

    @RequestMapping("/testCreateAMQP")
    public void testCreateAMQP() {
        amqpAdmin.declareExchange(new DirectExchange("myexchange.demo"));
        amqpAdmin.declareQueue(new Queue("myqueues.demo", true));
        amqpAdmin.declareBinding(new Binding("myqueues.demo", Binding.DestinationType.QUEUE, "myexchange.demo", "*.demo", null));
    }
}
