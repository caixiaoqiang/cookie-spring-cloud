package com.cookie.controller;

import com.cookie.feign.ServiceAFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final static Logger logger = LoggerFactory.getLogger(TestController.class);
    @Value("${spring.application.name}")
    private String applicationName ;

    @Autowired
    ServiceAFeign serviceAFeign ;

    @Value("${info.profile}")
    private String profile ;

    @GetMapping("")
    public String add(@RequestParam("a") int a , @RequestParam("b") int b ){

        logger.info("============="+profile);
        return serviceAFeign.add(a,b) ;
    }
}
