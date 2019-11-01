package com.fh.shop.api.consignee.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConst;
import com.fh.shop.api.consignee.biz.IConsigneeService;
import com.fh.shop.api.consignee.po.Consignee;
import com.fh.shop.api.members.vo.MembersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/consignee")
public class ConsigneeController {

    @Resource(name = "consigneeService")
    private IConsigneeService consigneeService;
    @Autowired
    private HttpServletRequest request;

    @GetMapping
    @Check
    public ServerResponse list(){
        MembersVo membersVo = (MembersVo) request.getAttribute(SystemConst.MEMBERS_INFO);
        Long membersVoId = membersVo.getId();
        return consigneeService.findList(membersVoId);
    }

    @PostMapping
    @Check
    public ServerResponse add(Consignee consignee){
        MembersVo membersVo = (MembersVo) request.getAttribute(SystemConst.MEMBERS_INFO);
        consignee.setMembersId(membersVo.getId());
        return consigneeService.addConsignee(consignee);
    }
}
