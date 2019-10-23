package com.cookie;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chenlei on 2017/8/14.
 */
public class LabelAndWeightMetadataRule extends ZoneAvoidanceRule {
    private AtomicInteger nextServerCyclicCounter;
    private final static Logger logger = LoggerFactory.getLogger(LabelAndWeightMetadataRule.class);

    public static final String META_DATA_KEY_LABEL_AND = "labelAnd";
    public static final String META_DATA_KEY_WEIGHT = "weight";
    public static final String APP_NAME_PREFIX = "service-";

    private Random random = new Random();

    public LabelAndWeightMetadataRule() {
        this.nextServerCyclicCounter = new AtomicInteger(0);
    }

    @Override
    public Server choose(Object key) {
        logger.info("choose server...");
        List<Server> serverList = this.getPredicate().getEligibleServers(this.getLoadBalancer().getReachableServers(), key);

        if (CollectionUtils.isEmpty(serverList)) {
            return null;
        }

        // 计算总值并剔除0权重节点
        int totalWeight = 0,labelTotalWeight = 0;
        Map<Server, Integer> serverWeightMap = new HashMap<>();
        Map<Server, Integer> labelServerWeightMap = new HashMap<>();
        for (Server server : serverList) {
            Map<String, String> metadata = ((DiscoveryEnabledServer) server).getInstanceInfo().getMetadata();
            String appName = ((DiscoveryEnabledServer) server).getInstanceInfo().getAppName().toLowerCase();
            if (appName.contains(APP_NAME_PREFIX)) {
                appName = appName.split(APP_NAME_PREFIX)[1];
            }
            //匹配项目的版本号
            String labelAnd = metadata.get(META_DATA_KEY_LABEL_AND);
            if(!StringUtils.isEmpty(labelAnd)){
                Optional<Map<String, String>> serversMapOpt = Optional.ofNullable(CoreHeaderInterceptor.label.get());
                if (serversMapOpt.isPresent()) {
                    String labelVersion = serversMapOpt.get().get(appName);
                    if (!StringUtils.isEmpty(labelVersion)) {
                        if (labelAnd.contains(labelVersion)) {
                            String strWeight = metadata.get(META_DATA_KEY_WEIGHT);
                            int weight = 100;
                            try {
                                weight = Integer.parseInt(strWeight);
                            } catch (Exception e) {
                                // 无需处理
                            }
                            if (weight <= 0) {
                                continue;
                            }
                            labelServerWeightMap.put(server, weight);
                            labelTotalWeight += weight;
                            continue;
                        }
                    }
                }
            }

            String strWeight = metadata.get(META_DATA_KEY_WEIGHT);

            int weight = 100;
            try {
                weight = Integer.parseInt(strWeight);
            } catch (Exception e) {
                // 无需处理
            }
            if (weight <= 0) {
                continue;
            }
            serverWeightMap.put(server, weight);
            totalWeight += weight;
        }

        if (labelTotalWeight != 0) {
//            int randomWight = this.random.nextInt(labelTotalWeight);
//            int current = 0;
//            for (Map.Entry<Server, Integer> entry : labelServerWeightMap.entrySet()) {
//                current += entry.getValue();
//                if (randomWight <= current) {
//                    return entry.getKey();
//                }
//            }
            List<Server> list = new ArrayList<>();
            for (Map.Entry<Server, Integer> entry : labelServerWeightMap.entrySet()) {
                list.add(entry.getKey());
            }

            return roundRobinRule(list);
        }

        // 权重随机
//        int randomWight = this.random.nextInt(totalWeight);
//        int current = 0;
//        for (Map.Entry<Server, Integer> entry : serverWeightMap.entrySet()) {
//            current += entry.getValue();
//            if (randomWight <= current) {
//                return entry.getKey();
//            }
//        }
        List<Server> list = new ArrayList<>();
        for (Map.Entry<Server, Integer> entry : serverWeightMap.entrySet()) {
            list.add(entry.getKey());
        }

        return roundRobinRule(list);
//        return null;
    }

    private Server roundRobinRule (List<Server> serverList) {
        Server server = null;
        int count = 0;

        while(true) {
            if(server == null && count++ < 10) {
                int serverCount = serverList.size();
                if(serverCount != 0) {
                    int nextServerIndex = this.incrementAndGetModulo(serverCount);
                    server = serverList.get(nextServerIndex);
                    if(server == null) {
                        Thread.yield();
                    } else {
                        if(server.isAlive() && server.isReadyToServe()) {
                            return server;
                        }

                        server = null;
                    }
                    continue;
                }

                logger.warn("No up servers available from load balancer");
                return null;
            }

            if(count >= 10) {
                logger.warn("No available alive servers after 10 tries from load balancer");
            }

            return server;
        }
    }

    private int incrementAndGetModulo(int modulo) {
        int current;
        int next;
        do {
            current = this.nextServerCyclicCounter.get();
            next = (current + 1) % modulo;
        } while(!this.nextServerCyclicCounter.compareAndSet(current, next));

        return next;
    }
}
