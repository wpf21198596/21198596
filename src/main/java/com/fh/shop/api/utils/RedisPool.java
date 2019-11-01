package com.fh.shop.api.utils;

import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

public class RedisPool {

    private RedisPool(){

    }

    private static JedisCluster jedisCluster;

    private static void initPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMinIdle(100);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setTestOnBorrow(true);
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        HostAndPort hostAndPort1 = new HostAndPort("192.168.131.128", 7001);
        HostAndPort hostAndPort2 = new HostAndPort("192.168.131.128", 7002);
        HostAndPort hostAndPort3 = new HostAndPort("192.168.131.128", 7003);
        HostAndPort hostAndPort4 = new HostAndPort("192.168.131.128", 7004);
        HostAndPort hostAndPort5 = new HostAndPort("192.168.131.128", 7005);
        HostAndPort hostAndPort6 = new HostAndPort("192.168.131.128", 7006);
        nodes.add(hostAndPort1);
        nodes.add(hostAndPort2);
        nodes.add(hostAndPort3);
        nodes.add(hostAndPort4);
        nodes.add(hostAndPort5);
        nodes.add(hostAndPort6);
        jedisCluster = new JedisCluster(nodes, jedisPoolConfig);
    }

    static {
        initPool();
    }

    public static JedisCluster getResource(){
        return jedisCluster;
    }
}
