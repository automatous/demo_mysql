package com.example.demo.service;

import com.example.demo.dao.StudentDao;
import org.springframework.scheduling.annotation.Async;

public class StudentService {
    private StudentDao studentDao;

    public StudentDao getStudentDao() {
        return studentDao;
    }

    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Async
    public String foo() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            Thread.sleep(1000);
            System.out.println("foo..............................");
        }
        return "foo....";
    }
}
