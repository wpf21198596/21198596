package com.fh.shop.api.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

public class RedisUtil {

    public static void set(String key,String value){
        JedisCluster resource = null;
        try {
            resource = RedisPool.getResource();
            resource.set(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Long del(String key){
        JedisCluster resource = null;
        Long del=0L;
        try {
            resource = RedisPool.getResource();
            del = resource.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return del;
    }

    public static String get(String key){
        JedisCluster resource = null;
        String value;
        try {
            resource = RedisPool.getResource();
            value = resource.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return value;
    }

    public static void expire(String key,int seconds){
        JedisCluster resource = null;
        try {
            resource = RedisPool.getResource();
            resource.expire(key,seconds);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void setEx(String key,String value,int seconds){
        JedisCluster resource = null;
        try {
            resource = RedisPool.getResource();
            resource.setex(key, seconds, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static boolean exist(String key){
        JedisCluster resource =null;
        boolean exist=false;
        try {
            resource = RedisPool.getResource();
            exist=resource.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            //抛异常
            throw new RuntimeException(e.getMessage());
        }
        return exist;
    }

    public static String hget(String key,String filed){
        JedisCluster resource = null;
        String value;
        try {
            resource = RedisPool.getResource();
            value = resource.hget(key,filed);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return value;
    }


    public static void hset(String key,String filed,String value){
        JedisCluster resource = null;
        try {
            resource = RedisPool.getResource();
            resource.hset(key, filed, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void hdel(String key,String filed){
        JedisCluster resource = null;
        try {
            resource = RedisPool.getResource();
            resource.hdel(key,filed);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void setex(String key,int seconds,String value){
        JedisCluster resource = null;
        try {
            resource = RedisPool.getResource();
            resource.setex(key, seconds, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
