package com.example.demo.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RemindSchedule {
    @Scheduled(cron = "5 * * * * ?")
    public void callMe() {
        System.out.println(new Date() + ": call me....");
    }
}
