package com.fh.shop.api.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SmsUtil {
    //发送验证码的请求路径URL
    private static final String URL="https://api.netease.im/sms/sendcode.action";
    //网易云信分配的账号，请替换你在管理后台应用下申请的Appkey
    private static final String APP_KEY="91b665661d1fdfdf38776bf7e03f93a3";
    //网易云信分配的密钥，请替换你在管理后台应用下申请的appSecret
    private static final String APP_SECRET="883d1c0f3d8c";

    public  static String smsPost(String phone){
        //设置头信息
        String NONCE = UUID.randomUUID().toString();
        String curTime = new Date().getTime() + "";
        String checkSum = CheckSumBuilder.getCheckSum(APP_SECRET, NONCE, curTime);
        Map<String,String> header = new HashMap<>();
        header.put("AppKey",APP_KEY);
        header.put("Nonce",NONCE);
        header.put("CurTime",curTime);
        header.put("CheckSum",checkSum);

        //设置参数信息
        Map<String,String> params = new HashMap<>();
        params.put("mobile",phone);

        String msg = HttpClientUtil.postMsg(URL, header, params);

        return msg;
    }
}
