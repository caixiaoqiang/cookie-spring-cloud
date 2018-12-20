package com.cookie.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * author : cxq
 * Date : 2018/12/20
 */

@FeignClient("service-a")
public interface ServiceAFeign {

    @GetMapping("/test")
    public String  add(@RequestParam("a") int a , @RequestParam("b") int b );
}
