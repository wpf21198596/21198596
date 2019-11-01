package com.fh.shop.api.brand.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.brand.biz.IBrandService;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/brands")
public class BrandController {

    @Resource(name = "brandService")
    private IBrandService brandService;

    @GetMapping
    @Check
    public ServerResponse findList(){
        ServerResponse list = brandService.findList();
        return list;
    }

  /*  @RequestMapping( method = RequestMethod.POST)
    public ServerResponse add(Brand brand){
        return brandService.add(brand);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ServerResponse delOne(@PathVariable Long id) {
        return brandService.delOne(id);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ServerResponse findById(@PathVariable Long id){
        return brandService.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ServerResponse update(@RequestBody Brand brand){
        return brandService.update(brand);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ServerResponse findAll(){
        return brandService.findAll();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ServerResponse delBatch(String ids){
        return brandService.delBatch(ids);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ServerResponse querPageList(@RequestBody BrandParam brandParam){
        return  brandService.querPageList(brandParam);
    }*/

}
