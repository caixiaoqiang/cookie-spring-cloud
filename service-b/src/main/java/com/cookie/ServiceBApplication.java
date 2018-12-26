package com.cookie;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients		// 允许feign调用
@EnableCircuitBreaker	// 允许熔断处理
public class ServiceBApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceBApplication.class, args);
	}

}

