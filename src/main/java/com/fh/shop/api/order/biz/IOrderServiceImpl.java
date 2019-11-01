package com.fh.shop.api.order.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.shop.api.cart.vo.CartVo;
import com.fh.shop.api.cart.vo.ShopListVo;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConst;
import com.fh.shop.api.members.vo.MembersVo;
import com.fh.shop.api.order.mapper.IOrderItemMapper;
import com.fh.shop.api.order.mapper.IOrderMapper;
import com.fh.shop.api.order.mapper.IPayLogMapper;
import com.fh.shop.api.order.po.Order;
import com.fh.shop.api.order.po.OrderItem;
import com.fh.shop.api.order.po.PayLog;
import com.fh.shop.api.product.mapper.IProductMapper;
import com.fh.shop.api.product.po.Shop;
import com.fh.shop.api.utils.DecimalUtil;
import com.fh.shop.api.utils.IdUtil;
import com.fh.shop.api.utils.KeyUtil;
import com.fh.shop.api.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("orderService")
public class IOrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderMapper orderMapper;
    @Autowired
    private IOrderItemMapper orderItemMapper;
    @Autowired
    private IProductMapper productMapper;
    @Autowired
    private IPayLogMapper payLogMapper;

    @Override
    public ServerResponse addOrder(Order order, MembersVo membersVo) {
        //获取订单详情
        String cartJson = RedisUtil.hget(SystemConst.CART_KEY, KeyUtil.buildCartRedisKey(membersVo.getId()));
        //判断订单是否为空
        if(StringUtils.isEmpty(cartJson)){
            return ServerResponse.error(ResponseEnum.CART_IS_NULL);
        }
        //转换为java实体类
        CartVo cartVo = JSONObject.parseObject(cartJson, CartVo.class);
        //生成订单ID
        String id=IdUtil.buildId();
        //商品详情补充及保存至数据库
        List<ShopListVo> shopListVos = getShopListVoList(order, membersVo, cartVo, id);
        //订单数据补充及保存至数据库
        buildOrder(order, membersVo, id);
        //支付日志存档
        buildPayLog(order, membersVo, id);
        //清空购物车
        RedisUtil.hdel(SystemConst.CART_KEY,KeyUtil.buildCartRedisKey(membersVo.getId()));
        return ServerResponse.success(shopListVos);
    }

    private void buildPayLog(Order order, MembersVo membersVo, String id) {
        PayLog payLog = new PayLog();
        payLog.setId(IdUtil.buildId());
        payLog.setCreateTime(new Date());
        payLog.setOrderId(id);
        payLog.setUserId(membersVo.getId());
        payLog.setPayMoney(order.getTotalMoney());
        payLog.setPayType(order.getPayType());
        payLog.setPayStatus(0);
        payLogMapper.insert(payLog);
        String payLogJson = JSONObject.toJSONString(payLog);
        RedisUtil.set(KeyUtil.buildPayLogKey(payLog.getUserId()),payLogJson);
    }

    private void buildOrder(Order order, MembersVo membersVo, String id) {
        order.setId(id);
        order.setUserId(membersVo.getId());
        order.setCreateDate(new Date());
        order.setStatus(SystemConst.NO_PAY);
        if(order.getTotalMoney()!=null){
            orderMapper.insert(order);
        }else {
            ServerResponse.error(ResponseEnum.SHOPLIST_IS_NULL);
        }
    }

    private List<ShopListVo> getShopListVoList(Order order, MembersVo membersVo, CartVo cartVo, String id) {
        List<ShopListVo> shopListVoList = cartVo.getShopListVoList();
        List<ShopListVo> shopListVos = new ArrayList<>();
        for (ShopListVo shopListVo : shopListVoList) {
            Shop shop = productMapper.selectById(shopListVo.getShopId());
            //库存足够
            if(shop.getStock()>=shopListVo.getCount()){
                OrderItem orderItem=new OrderItem();
                orderItem.setOrderId(id);
                orderItem.setShopName(shopListVo.getShopName());
                orderItem.setUserId(membersVo.getId());
                orderItem.setShopId(shopListVo.getShopId());
                orderItem.setCount(shopListVo.getCount());
                orderItem.setPrice(new BigDecimal(shopListVo.getPrice()));
                orderItem.setTotalPrice(new BigDecimal(shopListVo.getTotalPrice()));
                orderItem.setImg(shopListVo.getImg());
                //更新商品数据库
                shop.setStock(Integer.valueOf(shop.getStock())-Integer.valueOf(shopListVo.getCount()));
                UpdateWrapper<Shop> shopUpdateWrapper = new UpdateWrapper<>();
                shopUpdateWrapper.ge("stock",shopListVo.getCount());
                shopUpdateWrapper.eq("id",shopListVo.getShopId());
                int update = productMapper.update(shop, shopUpdateWrapper);
                //防止超卖
                if(update==0){
                    //库存不足
                    shopListVos.add(shopListVo);
                }else {
                    orderItemMapper.insert(orderItem);
                    //更新订单总价及总数量
                    if(order.getTotalCount()!=null && order.getTotalMoney()!=null){
                        order.setTotalCount(Integer.valueOf(order.getTotalCount())+Integer.valueOf(orderItem.getCount()));
                        order.setTotalMoney(DecimalUtil.sum(order.getTotalMoney().toString(),orderItem.getTotalPrice().toString()));
                    }else {
                        order.setTotalMoney(orderItem.getTotalPrice());
                        order.setTotalCount(orderItem.getCount());
                    }
                }
            }else {
                //库存不足
                shopListVos.add(shopListVo);
            }
        }
        return shopListVos;
    }
}
