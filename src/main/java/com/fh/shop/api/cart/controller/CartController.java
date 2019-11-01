package com.fh.shop.api.cart.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.cart.biz.ICartService;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.members.vo.MembersVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Resource(name = "cartService")
    private ICartService cartService;

    @PostMapping
    @Check
    public ServerResponse addCart(Long shopId,Integer count,MembersVo membersVo){
        return cartService.addCart(membersVo.getId(),shopId,count);
    }

    @GetMapping
    @Check
    public ServerResponse findCartList(MembersVo membersVo){
        return cartService.findCartList(membersVo.getId());
    }

    @DeleteMapping("/{shopId}")
    @Check
    public ServerResponse delShopList(@PathVariable Long shopId,MembersVo membersVo){
        return cartService.delShopList(membersVo.getId(),shopId);
    }

}
