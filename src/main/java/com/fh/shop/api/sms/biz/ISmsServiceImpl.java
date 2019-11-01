package com.fh.shop.api.sms.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConst;
import com.fh.shop.api.utils.KeyUtil;
import com.fh.shop.api.utils.RedisUtil;
import com.fh.shop.api.utils.SmsUtil;
import org.springframework.stereotype.Service;

@Service("smsService")
public class ISmsServiceImpl implements ISmsService {
    @Override
    public ServerResponse sendSms(String phoneNumbers) {
        //验证手机号
        if (phoneNumbers == null){
            //返回手机号为空
            return ServerResponse.error(ResponseEnum.PHONE_IS_NULL);
        }
        //获取验证码
        String code = SmsUtil.smsPost(phoneNumbers);
        //将验证码转换成json格式
        String JsonCode = JSONObject.toJSONString(code);
        //存放到redis中
        RedisUtil.setex(KeyUtil.buildSMSkey(phoneNumbers),SystemConst.SMSCODE_IS_NULL,JsonCode);
        return ServerResponse.success();
    }
}
