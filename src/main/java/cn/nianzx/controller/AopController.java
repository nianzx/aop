package cn.nianzx.controller;

import cn.nianzx.aop.MyAnnotation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试aop用Controller
 * Created by nianzx on 2019/1/25.
 */
@RestController
public class AopController {

    @MyAnnotation//自定义注解，实现有注解时才进入aop
    @RequestMapping("/index")
    public String index(String msg) {
        return "hello world!";
    }

    @RequestMapping("/around")
    public String around(String msg) {
        return "没有注解只进入Around";
    }
}
