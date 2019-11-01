package com.fh.shop.api.brand.biz;


import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.param.BrandParam;

public interface IBrandService {

    ServerResponse findList();

    ServerResponse delOne(Long id);

    ServerResponse findAll();

    ServerResponse add(Brand brand);

    ServerResponse findById(Long id);

    ServerResponse update(Brand brand);

    ServerResponse delBatch(String ids);

    ServerResponse querPageList(BrandParam brandParam);
}
