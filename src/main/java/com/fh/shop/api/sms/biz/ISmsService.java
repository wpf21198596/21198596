package com.fh.shop.api.sms.biz;

import com.fh.shop.api.common.ServerResponse;

public interface ISmsService {
    ServerResponse sendSms(String phoneNumbers);
}
