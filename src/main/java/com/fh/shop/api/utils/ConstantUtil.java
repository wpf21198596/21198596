package com.fh.shop.api.utils;

import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ConstantUtil {
    /**
     * 微信开发平台应用ID
     */
    public static final String APP_ID="wx2421b1c4370ec43b";
    /**
     * 应用对应的凭证
     */
    public static final String APP_SECRET="1add1a30ac87aa2db72f57a2375d8fec";
    /**
     * 应用对应的密钥
     */
    public static final String APP_KEY="1add1a30ac87aa2db72f57a2375d8fec";
    /**
     * 微信支付商户号
     */
    public static final String MCH_ID="10000100";
    /**
     * 商品描述
     */
    public static final String BODY="充值";
    /**
     * 商户号对应的密钥
     */
    public static final String PARTNER_key="*******";

    /**
     * 商户id
     */
    public static final String PARTNER_ID="*******";
    /**
     * 常量固定值
     */
    public static final String GRANT_TYPE="client_credential";
    /**
     * 获取预支付id的接口url
     */
    public static String GATEURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 微信服务器回调通知url
     */
    public static String NOTIFY_URL="http://url"; //可以访问的url
    /**
     * 微信服务器查询订单url
     */
    public static String ORDER_QUERY="https://api.mch.weixin.qq.com/pay/orderquery";

   /* Map<String, Object> getOrder(@RequestParam(value = "totalFee") String totalFee, @RequestParam(value = "deviceInfo") String deviceInfo,
                                 @RequestParam(value = "attach") String attach,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // 获取生成预支付订单的请求类
        PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);
//        String totalFee = request.getParameter("total_fee");
        int total_fee = (int) (Float.valueOf(totalFee) * 100);
        System.out.println("total:" + total_fee);
        System.out.println("total_fee:" + total_fee);
        prepayReqHandler.setParameter("appid", ConstantUtil.APP_ID);
        prepayReqHandler.setParameter("body", ConstantUtil.BODY);
        prepayReqHandler.setParameter("mch_id", ConstantUtil.MCH_ID);
        prepayReqHandler.setParameter("device_info", deviceInfo); //卡号
        prepayReqHandler.setParameter("attach", attach);//套餐值
        String nonce_str = WXUtil.getNonceStr();
        prepayReqHandler.setParameter("nonce_str", nonce_str);
        prepayReqHandler.setParameter("notify_url", ConstantUtil.NOTIFY_URL);
        out_trade_no = String.valueOf(UUID.next());
        prepayReqHandler.setParameter("out_trade_no", out_trade_no);
        prepayReqHandler.setParameter("spbill_create_ip", request.getRemoteAddr());
        String timestamp = WXUtil.getTimeStamp();
        prepayReqHandler.setParameter("time_start", timestamp);
        System.out.println(String.valueOf(total_fee));
        prepayReqHandler.setParameter("total_fee", String.valueOf(total_fee));
        prepayReqHandler.setParameter("trade_type", "APP");
        *//**
         * 注意签名（sign）的生成方式，具体见官方文档（传参都要参与生成签名，且参数名按照字典序排序，最后接上APP_KEY,转化成大写）
         *//*
        prepayReqHandler.setParameter("sign", prepayReqHandler.createMD5Sign());
        prepayReqHandler.setGateUrl(ConstantUtil.GATEURL);
        String prepayid = prepayReqHandler.sendPrepay();
        // 若获取prepayid成功，将相关信息返回客户端
        if (prepayid != null && !prepayid.equals("")) {
            String signs = "appid=" + ConstantUtil.APP_ID + "&noncestr=" + nonce_str + "&package=Sign=WXPay&partnerid="
                    + ConstantUtil.PARTNER_ID + "&prepayid=" + prepayid + "&timestamp=" + timestamp + "&key="
                    + ConstantUtil.APP_KEY;
            map.put("code", 0);
            map.put("info", "success");
            map.put("prepayid", prepayid);
            *//**
             * 签名方式与上面类似
             *//*
            map.put("sign", MD5Util.MD5Encode(signs, "utf8").toUpperCase());
            map.put("appid", ConstantUtil.APP_ID);
            map.put("device_info", deviceInfo);
            map.put("attach", attach);
            map.put("timestamp", timestamp);  //等于请求prepayId时的time_start
            map.put("noncestr", nonce_str);   //与请求prepayId时值一致
            map.put("package", "Sign=WXPay");  //固定常量
            map.put("partnerid", ConstantUtil.PARTNER_ID);
        } else {
            map.put("code", 1);
            map.put("info", "获取prepayid失败");
        }
        return map;
    }
    PrintWriter writer = response.getWriter();
    InputStream inStream = request.getInputStream();
    ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
        outSteam.write(buffer, 0, len);
    }
        outSteam.close();
        inStream.close();
    String result = new String(outSteam.toByteArray(), "utf-8");
        System.out.println("微信支付通知结果：" + result);
    Map<String, String> map = null;
        try {
        *//**
         * 解析微信通知返回的信息
         *//*
        map = XMLUtil.doXMLParse(result);
    } catch (JDOMException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }*/
}
