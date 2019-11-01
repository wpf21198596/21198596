package com.fh.shop.api.cart.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.cart.vo.CartVo;
import com.fh.shop.api.cart.vo.ShopListVo;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConst;
import com.fh.shop.api.product.mapper.IProductMapper;
import com.fh.shop.api.product.po.Shop;
import com.fh.shop.api.utils.DecimalUtil;
import com.fh.shop.api.utils.KeyUtil;
import com.fh.shop.api.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cartService")
public class ICartServiceImpl implements ICartService {

    @Autowired
    private IProductMapper productMapper;


    @Override
    public ServerResponse addCart(Long membersId, Long shopId, Integer count) {
        Shop shop = productMapper.selectById(shopId);
        //判断商品不存在
        if (null == shop){
            return ServerResponse.error(ResponseEnum.SHOP_IS_NULL);
        }
        //判断商品状态未上架
        if (shop.getIsUp() == SystemConst.SHOP_IS_NOT_UP){
            return ServerResponse.error(ResponseEnum.SHOP_IS_NOT_UP);
        }
        String cartJson = RedisUtil.hget(SystemConst.CART_KEY, KeyUtil.buildCartRedisKey(membersId));
        //判断未拥有购物车
        if (StringUtils.isEmpty(cartJson)){
            return buildcartVo(membersId, shopId, count, shop);
        }
        CartVo cartVo = JSONObject.parseObject(cartJson, CartVo.class);
        List<ShopListVo> shopListVoList = cartVo.getShopListVoList();
        for (ShopListVo shopListVo : shopListVoList) {
            //购物车中有该商品
            if (shopListVo.getShopId()==shopId){
                return updateCart(membersId, count, cartVo, shopListVoList, shopListVo);
            }
        }
        //购物车中没该商品
        ShopListVo shopListVo = addShopListVo(shopId, count, shop, cartVo);
        updateCartVo(count, shopListVo, cartVo);
        buildRedisCart(membersId, cartVo);
        return ServerResponse.success();
    }

    private ServerResponse updateCart(Long membersId, Integer count, CartVo cartVo, List<ShopListVo> shopListVoList, ShopListVo shopListVo) {
        update(count, shopListVo,cartVo,shopListVoList);
        buildRedisCart(membersId, cartVo);
        return ServerResponse.success();
    }


    @Override
    public ServerResponse findCartList(Long membersId) {
        String cartJson = RedisUtil.hget(SystemConst.CART_KEY, KeyUtil.buildCartRedisKey(membersId));
        if (StringUtils.isEmpty(cartJson)){
            return ServerResponse.error(ResponseEnum.CART_IS_NULL);
        }
        CartVo cartVo = JSONObject.parseObject(cartJson, CartVo.class);
        return ServerResponse.success(cartVo);
    }

    @Override
    public ServerResponse delShopList(Long membersId, Long shopId) {
        String shopListJson = RedisUtil.hget(SystemConst.CART_KEY,KeyUtil.buildCartRedisKey(membersId));
        if (StringUtils.isEmpty(shopListJson)){
            return ServerResponse.error(ResponseEnum.SHOP_IS_NULL);
        }
        CartVo cartVo = JSONObject.parseObject(shopListJson, CartVo.class);
        List<ShopListVo> shopListVoList = cartVo.getShopListVoList();
        for (ShopListVo shopListVo : shopListVoList) {
            if (shopListVo.getShopId()==shopId){
                update((0-Integer.valueOf(shopListVo.getCount())),shopListVo,cartVo,shopListVoList);
                shopListVoList.remove(shopListVo);
                break;
            }
        }
        buildRedisCart(membersId, cartVo);
        return ServerResponse.success();
    }

    private void update(Integer count, ShopListVo shopListVo,CartVo cartVo,List<ShopListVo> shopListVoList) {
        shopListVo.setCount(Integer.valueOf(count)+Integer.valueOf(shopListVo.getCount()));
        shopListVo.setTotalPrice(DecimalUtil.mul(shopListVo.getCount().toString(),shopListVo.getPrice()).toString());
        updateCartVo(count, shopListVo, cartVo);
        if(Integer.valueOf(shopListVo.getCount())==0){
            shopListVoList.remove(shopListVo);
        }
    }

    private ServerResponse buildcartVo(Long membersId, Long shopId, Integer count, Shop shop) {
        CartVo cartVo = new CartVo();
        cartVo.setTotalCount(count);
        cartVo.setTotalMoney(DecimalUtil.mul(count.toString(),shop.getPrice().toString()).toString());
        addShopListVo(shopId, count, shop, cartVo);
        buildRedisCart(membersId, cartVo);
        return ServerResponse.success();
    }

    private void updateCartVo(Integer count, ShopListVo shopListVo, CartVo cartVo) {
        cartVo.setTotalCount(Integer.valueOf(cartVo.getTotalCount())+Integer.valueOf(count));
        String addPrice=DecimalUtil.mul(count.toString(),shopListVo.getPrice()).toString();
        cartVo.setTotalMoney(DecimalUtil.sum(cartVo.getTotalMoney(),addPrice).toString());
    }



    private ShopListVo addShopListVo(Long shopId, Integer count, Shop shop, CartVo cartVo) {
        ShopListVo shopListVo = new ShopListVo();
        shopListVo.setCount(count);
        shopListVo.setImg(shop.getShopImg());
        shopListVo.setPrice(shop.getPrice().toString());
        shopListVo.setShopId(shopId);
        shopListVo.setShopName(shop.getShopName());
        shopListVo.setTotalPrice(DecimalUtil.mul(count.toString(),shop.getPrice().toString()).toString());
        cartVo.getShopListVoList().add(shopListVo);
        return shopListVo;
    }

    private void buildRedisCart(Long membersId, CartVo cartVo) {
        if (cartVo.getShopListVoList().size()==0){
            RedisUtil.hdel(SystemConst.CART_KEY,KeyUtil.buildCartRedisKey(membersId));
        }else{
            String cartJson1 = JSONObject.toJSONString(cartVo);
            RedisUtil.hset(SystemConst.CART_KEY,KeyUtil.buildCartRedisKey(membersId),cartJson1);
        }
    }
}
