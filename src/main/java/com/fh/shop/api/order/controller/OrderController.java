package com.fh.shop.api.order.controller;

import com.fh.shop.api.annotation.ApiIdempotent;
import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConst;
import com.fh.shop.api.members.vo.MembersVo;
import com.fh.shop.api.order.biz.IOrderService;
import com.fh.shop.api.order.po.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Resource(name = "orderService")
    private IOrderService orderService;

    @Check
    @ApiIdempotent
    @PostMapping
    public ServerResponse addOrder(Order order,MembersVo membersVo){
        return orderService.addOrder(order,membersVo);
    }

    public ServerResponse getPayLog(){
        return null;
    }
}
