package com.cookie.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * author : cxq
 * Date : 2018/12/20
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Value("${spring.application.name}")
    private String applicationName ;

    @GetMapping("")
    public String add(@RequestParam("a") int a , @RequestParam("b") int b ){
        int result = a  + b ;
        return applicationName + " get  result is "+result ;
    }
}
