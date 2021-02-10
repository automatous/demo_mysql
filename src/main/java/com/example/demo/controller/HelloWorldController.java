package com.example.demo.controller;

import com.example.demo.service.HttpClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {

    @Resource
//    private HttpClientService httpClientService;
    private HttpClientService httpAPIService;

    @RequestMapping("/hi")
    public String hi(String name) {
        return String.format("hi, %s~", name);
    }

    @GetMapping("/doGet")
    public String doGetHttpClient() throws Exception {
//        String res = httpClientService.doGet("https://bilibili.com");
        String res = httpAPIService.doGet("http://www.baidu.com");
        System.out.println(res);
        return res;
    }

    @PostMapping("/doPost")
    public String doPostHttpClient() throws Exception {
        String url = "http://localhost:8888/post";
        Map<String, Object> params = new HashMap<>();
        params.put("id", "123456");
        params.put("name", "yukino");
//        String res = httpClientService.doPost();
        String res = httpAPIService.doPost(url, params);
        System.out.println(res);
        return res;
    }

}
