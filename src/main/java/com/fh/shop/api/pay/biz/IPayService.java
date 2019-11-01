package com.fh.shop.api.pay.biz;

import com.fh.shop.api.common.ServerResponse;

public interface IPayService {

    ServerResponse createNative(Long id);

    ServerResponse queryStatus(Long id);
}
