package com.cookie.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
        try {
            Thread.sleep(5000);

        }catch (Exception e){
            return  "error";
        }
        return applicationName + " get  result is "+result ;

    }

//    public static void main(String[] args) {
//        List<Map<String,String>> list = new ArrayList<>();
//        Map<String,String> m1 = new HashMap<>();
//        m1.put("type","1");
//        list.add(m1);
//
//        Map<String,String> m2 = new HashMap<>();
//        m2.put("type","1");
//        list.add(m2);
//
//        Map<String,String> m3 = new HashMap<>();
//        m3.put("type","3");
//        list.add(m3);
//
//        Map<String,String> m4 = new HashMap<>();
//        m4.put("type","2");
//        list.add(m4);
//
//        Map<String,String> m5 = new HashMap<>();
//        m5.put("type","1");
//        list.add(m5);
//
//        Map<String,String> m6 = new HashMap<>();
//        m6.put("type","2");
//        list.add(m6);
//
//        list.forEach(item ->{
//            System.out.println(item.get("type"));
//            System.out.println(item);
//        });
//
//        Collections.sort(list, new Comparator<Map<String, String>>() {
//            @Override
//            public int compare(Map<String, String> o1, Map<String, String> o2) {
//                if (Integer.valueOf(o1.get("type")) < Integer.valueOf(o2.get("type"))){
//                    return 1;
//                }
//                return  -1 ;
//            }
//        });
//        System.out.println(list);
//
//    }
}
