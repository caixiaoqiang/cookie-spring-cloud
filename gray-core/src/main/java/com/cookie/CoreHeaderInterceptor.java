package com.cookie;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * Created by chenlei on 2017/8/14.
 */
public class CoreHeaderInterceptor extends HandlerInterceptorAdapter {
    public static final String HEADER_LABEL = "x-label";

    public static final HystrixRequestVariableDefault<Map<String, String>> label = new HystrixRequestVariableDefault<>();


    public static void initHystrixRequestContext(Map<String, String> serversMap) {
        if (!HystrixRequestContext.isCurrentThreadInitialized()) {
            HystrixRequestContext.initializeContext();
        }
        if (serversMap != null && serversMap.size() > 0) {
            CoreHeaderInterceptor.label.set(serversMap);
        } else {
            CoreHeaderInterceptor.label.set(Collections.emptyMap());
        }
    }

    public static void shutdownHystrixRequestContext() {
        if (HystrixRequestContext.isCurrentThreadInitialized()) {
            HystrixRequestContext.getContextForCurrentThread().shutdown();
        }
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String label = request.getHeader(CoreHeaderInterceptor.HEADER_LABEL);
        if (StringUtils.isEmpty(label)) {
            CoreHeaderInterceptor.initHystrixRequestContext(Collections.emptyMap());
        }else {
            Map<String, String> jsonArray = (Map<String, String>)JSONObject.parse(label);

            CoreHeaderInterceptor.initHystrixRequestContext(jsonArray);
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        CoreHeaderInterceptor.shutdownHystrixRequestContext();
    }



}
