package com.cookie.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * author : cxq
 * Date : 2018/12/20
 */

@FeignClient(name = "service-a", fallback = ServiceAFeign.HystrixClientFallback.class)
public interface ServiceAFeign {

    @GetMapping("/test/add")
    public String  add(@RequestParam("a") int a , @RequestParam("b") int b );



    @Component
    public    static  class  HystrixClientFallback implements  ServiceAFeign{

        @Override
        public String add(int a, int b) {
            return "this is fallback ! ";
        }
    }
}
