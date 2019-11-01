package com.fh.shop.api.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String Y_M_D="yyyy-MM-dd";
    public static final String FULL_YERA="yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSS="yyyyMMddHHmmss";


    public static final String data2str(Date date, String pattern){
        if(date==null){
            return "";
        }
        SimpleDateFormat sim=new SimpleDateFormat(pattern);
        String datestr=sim.format(date);
        return  datestr;
    }



}
