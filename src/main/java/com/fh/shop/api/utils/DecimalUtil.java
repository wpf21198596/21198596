package com.fh.shop.api.utils;

import java.math.BigDecimal;

public class DecimalUtil {

    public static BigDecimal mul(String s1,String s2){
        BigDecimal b1=new BigDecimal(s1);
        BigDecimal b2=new BigDecimal(s2);
        return b1.multiply(b2).setScale(2);
    }
    public static BigDecimal sum(String s1,String s2){
        BigDecimal b1=new BigDecimal(s1);
        BigDecimal b2=new BigDecimal(s2);
        return b1.add(b2).setScale(2);
    }
}
