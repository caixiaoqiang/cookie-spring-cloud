package com.cookie;

import com.alibaba.fastjson.JSON;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Map;


/**
 * Created by chenlei on 2017/8/14.
 */
@Configuration
@RibbonClients(defaultConfiguration = DefaultRibbonConfiguration.class)
public class CoreAutoConfiguration extends WebMvcConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(CoreAutoConfiguration.class);

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CoreHeaderInterceptor());
    }

    @Bean
    public RequestInterceptor headerInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                Map<String, String> serversMap = CoreHeaderInterceptor.label.get();
                logger.info("---------RequestInterceptor label:"+serversMap.toString());
                requestTemplate.header(CoreHeaderInterceptor.HEADER_LABEL, JSON.toJSONString(serversMap));
                if (serversMap != null && serversMap.size() > 0) {
                    requestTemplate.header(CoreHeaderInterceptor.HEADER_LABEL, JSON.toJSONString(serversMap));
                }else {
                    requestTemplate.header(CoreHeaderInterceptor.HEADER_LABEL, JSON.toJSONString(serversMap));
                }

            }
        };
    }


}
