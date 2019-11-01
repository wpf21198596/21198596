package com.fh.shop.api.members.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConst;
import com.fh.shop.api.members.biz.IMembersService;
import com.fh.shop.api.members.po.Members;
import com.fh.shop.api.members.vo.MembersVo;
import com.fh.shop.api.sms.entity.Sms;
import com.fh.shop.api.utils.KeyUtil;
import com.fh.shop.api.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(description = "用户操作接口")
@RestController
@RequestMapping("/members")
public class MembersController {

    @Resource(name = "membersService")
    private IMembersService membersService;
    @Autowired
    private HttpServletRequest request;

    @ApiOperation(value = "登陆接口",httpMethod = "post",notes = "登录后将用户信息加密同签名合并发送给前台")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "membersName",value = "登录账号",required = true,dataType = "String"),
            @ApiImplicitParam(name = "password",value = "登录密码",required = true,dataType = "String")
    })
    @PostMapping("/login")
    public ServerResponse login(Members members)    {
        return membersService.login(members);
    }

    @PostMapping("/login/phone")
    public ServerResponse loginByPhone(String PhoneNumbers){
        return membersService.loginByPhone(PhoneNumbers);
    }

    @PostMapping("/login/sms")
    public ServerResponse loginBySms(Sms sms){
        return membersService.loginBySms(sms);
    }

    @GetMapping
    @Check
    public ServerResponse findMembersInfo(){
        MembersVo membersVo = (MembersVo) request.getAttribute(SystemConst.MEMBERS_INFO);
        return ServerResponse.success(membersVo);
    }

    @GetMapping("/loginOut")
    @Check
    public ServerResponse loginOut(){
        MembersVo membersVo = (MembersVo) request.getAttribute(SystemConst.MEMBERS_INFO);
        String uuid = membersVo.getUuid();
        String membersName = membersVo.getMembersName();
        //清除redis、设置过期
        RedisUtil.del(KeyUtil.buildMembersRedisKey(membersName,uuid));
        return ServerResponse.success();
    }

}
