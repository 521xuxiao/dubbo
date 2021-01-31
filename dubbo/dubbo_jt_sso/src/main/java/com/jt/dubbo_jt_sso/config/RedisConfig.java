package com.jt.dubbo_jt_sso.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class RedisConfig {
    /**
     * 配置redis集群
     */
    @Value("${redis.nodes}")
    private String nodes;
    @Bean
    @Scope("prototype")
    public JedisCluster jedisCluster() {
        Set<HostAndPort> setNode = new HashSet<>();
        String[] arrayNodes = nodes.split(",");
        for (String node: arrayNodes) {
            String host = node.split(":")[0];
            int port = Integer.parseInt(node.split(":")[1]);
            HostAndPort hostAndPort = new HostAndPort(host, port);
            setNode.add(hostAndPort);
        }
        return new JedisCluster(setNode);
    }
}
