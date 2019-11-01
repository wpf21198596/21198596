package com.fh.shop.api.sms.controller;

import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.sms.biz.ISmsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sms")
public class SmsController {

    @Resource(name = "smsService")
    private ISmsService smsService;

    @GetMapping
    public ServerResponse sendSms(String PhoneNumbers){

        return smsService.sendSms(PhoneNumbers);
    }
}
