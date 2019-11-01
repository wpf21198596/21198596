package com.fh.shop.api.product.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.product.mapper.IProductMapper;
import com.fh.shop.api.product.po.Shop;
import com.fh.shop.api.product.vo.ShopVo;
import com.fh.shop.api.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("productService")
public class IProductServiceImpl implements IProductService {

    @Autowired
    private IProductMapper productMapper;


    @Override
    public ServerResponse findList() {
        String shopListStr = RedisUtil.get("shopList");
        if(StringUtils.isNotEmpty(shopListStr)){
            List<ShopVo> shopVoList = JSONObject.parseArray(shopListStr, ShopVo.class);
            return ServerResponse.success(shopVoList);
        }
        QueryWrapper<Shop> shopQueryWrapper = new QueryWrapper<>();
        shopQueryWrapper.orderByDesc("id");
        shopQueryWrapper.eq("isUp",1);
        List<Shop> shopList = productMapper.selectList(shopQueryWrapper);
        List<ShopVo> shopVoList = buildShopVoList(shopList);
        String jsonString = JSONObject.toJSONString(shopVoList);
        RedisUtil.setEx("shopList",jsonString,20);
        return ServerResponse.success(shopVoList);
    }

    private List<ShopVo> buildShopVoList(List<Shop> shopList) {
        List<ShopVo> shopVoList=new ArrayList<>();
        for (Shop shop : shopList) {
            ShopVo shopVo = buildShopVo(shop);
            shopVoList.add(shopVo);
        }
        return shopVoList;
    }

    private ShopVo buildShopVo(Shop shop) {
        ShopVo shopVo=new ShopVo();
        shopVo.setId(shop.getId());
        shopVo.setShopName(shop.getShopName());
        shopVo.setPrice(shop.getPrice().toString());
        shopVo.setShopImg(shop.getShopImg());
        shopVo.setProductTime(shop.getProductTime());
        shopVo.setStock(shop.getStock());
        shopVo.setIsHot(shop.getIsHot());
        shopVo.setIsUp(shop.getIsUp());
        return shopVo;
    }
}
