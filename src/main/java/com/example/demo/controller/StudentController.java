package com.example.demo.controller;

import com.example.demo.bean.Student;
import com.example.demo.mapper.StuMapper;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class StudentController {
    @Autowired
    StuMapper stuMapper;

    @Autowired
    StudentService studentService;  // 已经通过config注入ioc

//    @Autowired
//    RedisTemplate autoRedisTemplate;    // error! No qualifying bean of type 'org.springframework.data.redis.core.RedisTemplate<?, ?>' available: expected single matching bean but found 2: redisTemplate,stringRedisTemplate

//    @Autowired
//    RedisTemplate redisTemplate;    // 名称匹配成功

//    @Autowired
//    RedisTemplate<Object, Object> autoRedisTemplate;  // 泛型匹配成功

//    @Autowired
//    RedisTemplate<Integer, Student> redisTemplate;    // error! No qualifying bean of type 'org.springframework.data.redis.core.RedisTemplate<java.lang.Integer, com.example.demo.bean.Student>' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}

    @Resource
    RedisTemplate<Integer, Student> redisTemplate;    // 名称匹配成功

//    @Resource
//    RedisTemplate<Integer, Student> re;    // error! No qualifying bean of type 'org.springframework.data.redis.core.RedisTemplate<java.lang.Integer, com.example.demo.bean.Student>' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@javax.annotation.Resource(shareable=true, lookup=, name=, description=, authenticationType=CONTAINER, type=class java.lang.Object, mappedName=)}

//    @Resource
//    RedisTemplate<Object, Object> re; // 类型匹配成功

//    @Resource
//    RedisTemplate re; //  expected single matching bean but found 2:

//    @Resource
//    RedisTemplate redisTemplate; // 名称匹配成功


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/addStu")
    public boolean addStu(Student stu) {
        return stuMapper.addStu(stu);
    }

    @RequestMapping("/deleteStuByStuno/{stuno}")
    public boolean deleteStuByStuno(@PathVariable("stuno") Integer stuno) {
        return stuMapper.deleteStuByStuno(stuno);
    }

    @RequestMapping("/updateStuByStuno")
    public boolean updateStuByStuno(Student student) {
        return stuMapper.updateStuByStuno(student);
    }

    @RequestMapping("/queryStus")
    public List<Student> queryStus() {
        return stuMapper.queryStus();
    }

    @Cacheable(cacheNames = {"stu"}, key = "#stuno")
    @RequestMapping("queryByStuno")
    public Student queryByStuno(Integer stuno) {
        Student ret = stuMapper.queryStuByStuno(stuno);
        return ret;
    }


    @Cacheable(cacheNames = {"stu"}, key = "#p0")
    @RequestMapping("queryByStuno2")
    public Student queryByStuno2(Integer stuno) {
        Student ret = stuMapper.queryStuByStuno(stuno);
        return ret;
    }

    @Cacheable(cacheNames = {"stu"}, key = "#a0")
    @RequestMapping("queryByStuno2_1")
    public Student queryByStuno2_1(Integer stuno) {
        Student ret = stuMapper.queryStuByStuno(stuno);
        return ret;
    }

    @Cacheable(cacheNames = {"stu"}, key = "stu.stuno")
    @RequestMapping("queryByStuno3")
    public Student queryByStuno3(Student stu) {
        Student ret = stuMapper.queryStuByStuno(stu.getStuno());
        return ret;
    }


    @Cacheable(cacheNames = {"stu"}, key = "#p0.stuno")
    @RequestMapping("queryByStuno4")
    public Student queryByStuno4(Student stu) {
        Student ret = stuMapper.queryStuByStuno(stu.getStuno());
        return ret;
    }

    @Cacheable(cacheNames = {"stu", "abc"}, key = "#root.caches[0].name")
    @RequestMapping("queryByStuno5")
    public Student queryByStuno5(Student stu) {
        Student ret = stuMapper.queryStuByStuno(stu.getStuno());
        return ret;
    }

    @Cacheable(cacheNames = {"stu"}, key = "args[0]")
    @RequestMapping("queryByStuno6/{stuno}")
    public Student queryByStuno6(@PathVariable("stuno") Integer stuno) {
        Student ret = stuMapper.queryStuByStuno(stuno);
        return ret;
    }

    @Cacheable(cacheNames = {"stu"}, unless = "#result==null")
    @RequestMapping("queryByStuno9/{stuno}")
    public Student queryByStuno9(@PathVariable("stuno") Integer stuno) {
        Student ret = stuMapper.queryStuByStuno(stuno);
        return ret;
    }

//    @RequestMapping("/testRedisTemplate")
//    public Object testRedisTemplate(int stuno) {
//        autoRedisTemplate.opsForValue().set(stuno, stuMapper.queryStuByStuno(stuno));
//        return autoRedisTemplate.opsForValue().get(stuno);
//
////        redisTemplate.opsForValue().set(stuno, stuMapper.queryStuByStuno(stuno));
////        return redisTemplate.opsForValue().get(stuno);
//    }

//    @RequestMapping("/testRedisTemplateGeneric")
//    public Object testRedisTemplateGeneric(int stuno) {
//        redisTemplate.opsForValue().set(stuno, stuMapper.queryStuByStuno(stuno));
//        return redisTemplate.opsForValue().get(stuno);
//    }

    @RequestMapping("/testStringRedisTemplate")
    public String testStringRedisTemplate() {
        stringRedisTemplate.opsForValue().set("levelup", "2020-02-05 01:48");
        return stringRedisTemplate.opsForValue().get("levelup");
    }

    @RequestMapping("/testAsync")
    public String testAsync() throws InterruptedException {
        String foo = studentService.foo();  // 因为是异步所以这里直接返回null了，而不会阻塞在这里等返回结果（PS: 要想获取返回值，估计只能用future模式了吧）
        return foo;
    }
}