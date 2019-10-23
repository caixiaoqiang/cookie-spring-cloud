package com.cookie;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenlei on 2017/8/22.
 */
public class CoreInitLabel {

    //获取项目的标签map
    public Map<String, Map<String, String>> getServerMap() {
        Map<String, Map<String, String>> serverMap = new HashMap<>();

        Map<String,String> serviceA = new HashMap<>();
        serviceA.put("product","v2");
        serviceA.put("test","v1");

        Map<String,String> serviceB = new HashMap<>();
        serviceB.put("product","v1");
        serviceB.put("test","v1");

        serverMap.put("a",serviceA);
        serverMap.put("b",serviceB);

        return  serverMap ;
    }

    /**
     *
     * @param labelVersion  test/product
     * @return
     */
    public Map<String, String> initLabel(String labelVersion) {
        // 从redis获取服务列表及当前版本号
        Map<String, Map<String, String>> serversMap = getServerMap();
        Map<String, String> serverMap = new HashMap<>();
        if (serversMap != null && serversMap.size() > 0) {
            for (Map.Entry<String, Map<String, String>> server : serversMap.entrySet()) {
                // 获取测试用户的版本号
                serverMap.put(server.getKey(), server.getValue().get(labelVersion));
            }
        }
        CoreHeaderInterceptor.initHystrixRequestContext(serverMap);
        return serverMap;
    }
}
