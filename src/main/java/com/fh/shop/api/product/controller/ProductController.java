package com.fh.shop.api.product.controller;

import com.fh.shop.api.product.biz.IProductService;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Resource(name = "productService")
    private IProductService productService;

    @GetMapping
    public ServerResponse findList(){
        return productService.findList();
    }
}
