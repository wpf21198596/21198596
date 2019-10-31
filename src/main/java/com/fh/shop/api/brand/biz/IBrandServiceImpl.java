package com.fh.shop.api.brand.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.brand.mapper.IBrandMapper;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.param.BrandParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("brandService")
public class IBrandServiceImpl implements IBrandService {

    @Autowired
    private IBrandMapper brandMapper;

    @Override
    public ServerResponse findList() {
        QueryWrapper<Brand> brandQueryWrapper = new QueryWrapper<>();
        brandQueryWrapper.eq("isHot",1);
        brandQueryWrapper.orderByDesc("orderId");
        List<Brand> list=brandMapper.selectList(brandQueryWrapper);
        return ServerResponse.success(list);
    }

    @Override
    public ServerResponse delOne(Long id) {
        brandMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findAll() {
        List<Brand> brands = brandMapper.selectList(null);
        return ServerResponse.success(brands);
    }

    @Override
    public ServerResponse add(Brand brand) {
        brandMapper.insert(brand);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findById(Long id) {
        Brand brand = brandMapper.selectById(id);
        return ServerResponse.success(brand);
    }

    @Override
    public ServerResponse update(Brand brand) {
        brandMapper.updateById(brand);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse delBatch(String ids) {
        if(StringUtils.isEmpty(ids)){
            return ServerResponse.error();
        }
        List<Long> idsArr=new ArrayList<>();
        String[] strings = ids.split(",");
        for (String string : strings) {
            idsArr.add(Long.valueOf(string));
        }
        brandMapper.deleteBatchIds(idsArr);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse querPageList(BrandParam brandParam) {
        QueryWrapper<Brand> brandQueryWrapper = new QueryWrapper<>();
        brandQueryWrapper.eq("orderId",brandParam.getOrderId());
        List<Brand> brandList = brandMapper.selectList(brandQueryWrapper);
        return ServerResponse.success(brandList);
    }


}
