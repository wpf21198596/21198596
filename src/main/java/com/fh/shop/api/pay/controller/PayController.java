package com.fh.shop.api.pay.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.members.vo.MembersVo;
import com.fh.shop.api.pay.biz.IPayService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Resource(name = "payService")
    private IPayService payService;

    @RequestMapping("/createNative")
    @Check
    public ServerResponse createNative(MembersVo membersVo){
        return payService.createNative(membersVo.getId());
    }

    @RequestMapping("/queryStatus")
    @Check
    public ServerResponse queryStatus(MembersVo membersVo){
        return payService.queryStatus(membersVo.getId());
    }

}
