package com.haiyang.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // 当前类为控制器类
@RequestMapping("/index")
@CrossOrigin
public class IndexController {

    // 处理请求方法，方法返回数据，就是响应给前端的数据。
    @RequestMapping("/test") //访问 /index/test 返回 "Hello SpringBoot"
    public String test() {
        return "Hello SpringBoot";
    }
}
