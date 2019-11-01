package com.fh.shop.api.type.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.type.biz.ITypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("types")
public class TypeController {

    @Resource(name = "typeService")
    private ITypeService typeService;

    @GetMapping
    @Check
    public ServerResponse findAllList(){
        return typeService.findAllList();
    }

}
