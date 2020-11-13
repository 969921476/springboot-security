package com.springboot.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: ChenJunNan
 * @Description:
 * @Date: create in 2020/11/13 10:34
 */
@Controller
public class TestController {
    @RequestMapping({"/","/index"})
    public String index() {
        return "Index";
    }
    @RequestMapping("/tologin")
    public String login() {
        return "login";
    }
    @RequestMapping("/html1/{id}")
    public String html1(@PathVariable int id) {
        return "html1/html"+id;
    }
    @RequestMapping("/html2/{id}")
    public String html2(@PathVariable int id) {
        return "html2/html"+id;
    }
}
