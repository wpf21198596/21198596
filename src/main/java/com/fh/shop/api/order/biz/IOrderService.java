package com.fh.shop.api.order.biz;

import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.members.vo.MembersVo;
import com.fh.shop.api.order.po.Order;

public interface IOrderService {
    ServerResponse addOrder(Order order, MembersVo membersVo);
}
