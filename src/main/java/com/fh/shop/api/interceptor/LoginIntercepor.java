package com.fh.shop.api.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.SystemConst;
import com.fh.shop.api.exception.GlobalException;
import com.fh.shop.api.members.vo.MembersVo;
import com.fh.shop.api.utils.KeyUtil;
import com.fh.shop.api.utils.Md5Util;
import com.fh.shop.api.utils.RedisUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Base64;

public class LoginIntercepor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        //处理跨域，头信息
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*");
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"x-auth,x-token,content-type");
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"DELETE,POST,GET,PUT");
        //判断options请求
        String methodType = request.getMethod();
        if(methodType.equalsIgnoreCase("options")){
            return false;
        }
        //通过自定义注解判断是否会员操作
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if(!method.isAnnotationPresent(Check.class)){
            return true;
        }
        //获取头部信息
        String authHeader = request.getHeader("x-auth");
        //头部信息丢失
        if(StringUtils.isEmpty(authHeader)){
            throw new GlobalException(ResponseEnum.HEADERINFO_IS_LOSE);
        }
        //头部信息正常
        String[] split = authHeader.split("\\.");
        if (split.length!=2){
            throw new GlobalException(ResponseEnum.HEADERINFO_IS_NOTALL);
        }
        //验证数据是否被篡改
        String membersNameBase64=split[0];
        String signBase64=split[1];
        //生成新的签名
        String newSign = Md5Util.sign(membersNameBase64, SystemConst.SECRET_KEY);
        //新签名加密
        String encodeToString = Base64.getEncoder().encodeToString(newSign.getBytes());
        if (!encodeToString.equals(signBase64)){
            throw new GlobalException(ResponseEnum.HEADERINFO_IS_CHANGE);
        }
        //获取用户信息
        String s = new String(Base64.getDecoder().decode(membersNameBase64));
        MembersVo membersVo=JSONObject.parseObject(s,MembersVo.class);
        String membersName = membersVo.getMembersName();
        String uuid = membersVo.getUuid();
        //判断信息是否失效
        boolean exist = RedisUtil.exist(KeyUtil.buildMembersRedisKey(membersName, uuid));
        if(!exist){
            throw new GlobalException(ResponseEnum.LOGIN_TIME_OUT);
        }
        RedisUtil.expire(KeyUtil.buildMembersRedisKey(membersName, uuid),60*30);
        //将用户信息放到request作用域中
        request.setAttribute(SystemConst.MEMBERS_INFO,membersVo);
        return true;
    }
}
