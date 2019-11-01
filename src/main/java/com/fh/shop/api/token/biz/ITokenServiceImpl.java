package com.fh.shop.api.token.biz;

import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.utils.RedisUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("tokenService")
public class ITokenServiceImpl implements ITokenService {

    @Override
    public ServerResponse addToken() {
        String token = UUID.randomUUID().toString();
        RedisUtil.set(token,token);
        return ServerResponse.success(token);
    }
}
