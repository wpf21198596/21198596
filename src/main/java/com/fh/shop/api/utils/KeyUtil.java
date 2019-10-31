package com.fh.shop.api.utils;

public class KeyUtil {

    public static String buildSMSkey(String phone){
        return "sms"+phone;
    }
    public static String buildMembersRedisKey(String name , String uuid){
        return "Vip:"+name+":"+uuid;
    }
    public static String buildCartRedisKey(Long id){
        return "members:"+":"+id.toString();
    }
    public static String buildPayLogKey(Long id){
        return "payLog:"+id.toString();
    }
}
