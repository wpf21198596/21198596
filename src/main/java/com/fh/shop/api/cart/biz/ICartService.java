package com.fh.shop.api.cart.biz;

import com.fh.shop.api.common.ServerResponse;

public interface ICartService {

    ServerResponse addCart(Long membersId, Long shopId, Integer count);

    ServerResponse findCartList(Long membersId);

    ServerResponse delShopList(Long membersId, Long shopId);
}
