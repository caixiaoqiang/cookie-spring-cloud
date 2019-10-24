package com.cookie.controller;

import com.cookie.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

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


    @Value("${server.port}")
    private String port ;

//    @Autowired
//    private DataSource dataSource ;

    @GetMapping("add")
    public String add(@RequestParam("a") int a , @RequestParam("b") int b ){
        int result = a  + b ;
//        try {
//            Thread.sleep(5000);
//
//        }catch (Exception e){
//            return  "error";
//        }
        logger.info(" result = "+result);
        return applicationName + " get  result is "+result  + " and port = "+port;

    }

    @GetMapping("muti")
    public String muti(@RequestParam("a") int a , @RequestParam("b") int b ){
        int result = a  * b ;
//        try {
//            Thread.sleep(5000);
//
//        }catch (Exception e){
//            return  "error";
//        }
        logger.info(" result = "+result);
        return applicationName + " get  result is "+result  + " and port = "+port;

    }

//    @GetMapping("/property")
//    public void getConfigurationproperties() throws SQLException {
////        System.out.println(" property get : name  is "+config.getName() +" and tel is "+config.getTel());
////        System.out.println(" value get : name  is "+config.getName() +" and tel is "+config.getTel());
//
//        Connection connection = dataSource.getConnection();
//        connection.createStatement().execute("update accont set name = 'cookie' where id = 1  ");
//    }


}
