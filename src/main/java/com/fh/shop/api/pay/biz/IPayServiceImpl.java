package com.fh.shop.api.pay.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConst;
import com.fh.shop.api.order.mapper.IPayLogMapper;
import com.fh.shop.api.order.po.PayLog;
import com.fh.shop.api.utils.DateUtil;
import com.fh.shop.api.utils.DecimalUtil;
import com.fh.shop.api.utils.KeyUtil;
import com.fh.shop.api.utils.RedisUtil;
import com.fh.shop.api.wxpay.MyConfig;
import com.fh.shop.api.wxpay.WXPay;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("payService")
public class IPayServiceImpl implements IPayService {

    @Autowired
    private IPayLogMapper payLogMapper;

    @Override
    public ServerResponse createNative(Long id){
        String payLogJson = RedisUtil.get(KeyUtil.buildPayLogKey(id));
        if(StringUtils.isEmpty(payLogJson)){
            return ServerResponse.error(ResponseEnum.PAYLOG_IS_NULL);
        }
        PayLog payLog = JSONObject.parseObject(payLogJson, PayLog.class);
        BigDecimal money = DecimalUtil.mul(payLog.getPayMoney().toString(), "100");
        Date date = DateUtils.addMinutes(new Date(), 1);
        try {
        MyConfig myConfig = new MyConfig();
        WXPay wxpay = new WXPay(myConfig);
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", "飞狐电商平台");
        data.put("out_trade_no", payLog.getId());
        data.put("fee_type", "CNY");
        data.put("total_fee", money.intValue()+"");
        data.put("time_expire", DateUtil.data2str(date,DateUtil.YYYYMMDDHHMMSS));//失效时间
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        Map<String, String> resp = wxpay.unifiedOrder(data);
            String return_code = resp.get("return_code");
            String return_msg = resp.get("return_msg");
            if (!return_code.equalsIgnoreCase("success")){
                return ServerResponse.error(55555,"错误："+return_msg);
            }
            String result_code = resp.get("result_code");
            String err_code_des = resp.get("err_code_des");
            if (!result_code.equalsIgnoreCase("success")){
                return ServerResponse.error(55555,"错误："+err_code_des);
            }
            Map map=new HashMap();
            String code_url = resp.get("code_url");
            map.put("code_url",code_url);
            map.put("payMoney",payLog.getPayMoney());
            map.put("orderNo",payLog.getId());
            System.out.println(resp);
            return ServerResponse.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
    }

    @Override
    public ServerResponse queryStatus(Long id) {
        String payLogJson = RedisUtil.get(KeyUtil.buildPayLogKey(id));
        if(StringUtils.isEmpty(payLogJson)){
            return ServerResponse.error(ResponseEnum.PAYLOG_IS_NULL);
        }
        PayLog payLog = JSONObject.parseObject(payLogJson, PayLog.class);
        MyConfig config = new MyConfig();
        try {
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", payLog.getId());
            int count=0;
            while (true){
                Map<String, String> resp = wxpay.orderQuery(data);
                System.out.println(resp);
                String return_code = resp.get("return_code");
                String return_msg = resp.get("return_msg");
                if (!return_code.equalsIgnoreCase("success")){
                    return ServerResponse.error(55555,"错误："+return_msg);
                }
                String result_code = resp.get("result_code");
                String err_code_des = resp.get("err_code_des");
                if (!result_code.equalsIgnoreCase("success")){
                    return ServerResponse.error(55555,"错误："+err_code_des);
                }
                String trade_state = resp.get("trade_state");
                if (trade_state.equalsIgnoreCase("success")){//成功
                    String transaction_id = resp.get("transaction_id");
                    PayLog log = new PayLog();
                    log.setPayStatus(SystemConst.SUCCESS_PAY);
                    log.setPayTime(new Date());
                    log.setTransactionId(transaction_id);
                    log.setId(payLog.getId());
                    payLogMapper.updateById(log);
                    RedisUtil.del(KeyUtil.buildPayLogKey(id));
                    return ServerResponse.success();
                }
                count++;
                Thread.sleep(3000);
                if (count > 20){
                    return ServerResponse.error(ResponseEnum.TIME_OUT);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
    }
}
