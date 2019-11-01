package com.fh.shop.api.interceptor;

import com.fh.shop.api.annotation.ApiIdempotent;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.exception.GlobalException;
import com.fh.shop.api.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class IdempotentInterceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod= (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if(!method.isAnnotationPresent(ApiIdempotent.class)){
            return true;
        }
        String token = request.getHeader("x-token");
        if(StringUtils.isEmpty(token)){
            throw new GlobalException(ResponseEnum.HEADERINFO_IS_LOSE);
        }
        boolean exist = RedisUtil.exist(token);
        if (!exist){
            throw new GlobalException(ResponseEnum.TOKEN_HEADER_IS_LOSE);
        }
        Long del = RedisUtil.del(token);
        if (del<=0){
            throw new GlobalException(ResponseEnum.REQUEST_REPET_SEND);
        }
        return true;
    }
}
