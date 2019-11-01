package com.fh.shop.api.members.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConst;
import com.fh.shop.api.members.mapper.IMembersMapper;
import com.fh.shop.api.members.po.Members;
import com.fh.shop.api.members.vo.MembersVo;
import com.fh.shop.api.sms.entity.Sms;
import com.fh.shop.api.utils.KeyUtil;
import com.fh.shop.api.utils.Md5Util;
import com.fh.shop.api.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service("membersService")
public class IMembersServiceImpl implements IMembersService {

    @Autowired
    private IMembersMapper membersMapper;

    @Override
    public ServerResponse login(Members members) {
        String membersName = members.getMembersName();
        String password = members.getPassword();
        //判断账号为空
        if(StringUtils.isEmpty(membersName)){
            return ServerResponse.error(ResponseEnum.INPUTUSER_IS_NULL);
        }
        //判断密码为空
        if(StringUtils.isEmpty(password)){
            return ServerResponse.error(ResponseEnum.INPUTUSER_IS_NULL);
        }
        //查询账户信息
        QueryWrapper<Members> membersQueryWrapper = new QueryWrapper<>();
        membersQueryWrapper.eq("membersName",membersName);
        Members members1 = membersMapper.selectOne(membersQueryWrapper);
        //判断账号是否存在
        if(members1 ==null ){
            return ServerResponse.error(ResponseEnum.USER_IS_NULL);
        }
        //判断密码是否错误
        if(!members1.getPassword().equals(password)){
            return ServerResponse.error(ResponseEnum.UERPASSWORD_IS_NULL);
        }
        //返回给客户端的信息
        MembersVo membersVo = new MembersVo();
        String uuid = UUID.randomUUID().toString();
        membersVo.setId(members1.getId());
        membersVo.setUuid(uuid);
        membersVo.setMembersName(members1.getMembersName());
        membersVo.setRealName(members1.getRealName());
        //将用户信息用base64加密
        String jsonString = JSONObject.toJSONString(membersVo);
        String encodeToString = Base64.getEncoder().encodeToString(jsonString.getBytes());
        //生成签名
            String sign = Md5Util.sign(encodeToString, SystemConst.SECRET_KEY);
        //将签名进行加密
        String s = Base64.getEncoder().encodeToString(sign.getBytes());
        //根据用户名，UUID生成key
        String s1 = KeyUtil.buildMembersRedisKey(membersName, uuid);
        //存入Redis中，判断失效与否
        RedisUtil.setEx(s1,s,60*30);
        //返回数据（加密的用户信息+加密的签名）
        return ServerResponse.success(encodeToString+"."+s);
    }

    @Override
    public ServerResponse loginByPhone(String phoneNumbers) {
        if(StringUtils.isEmpty(phoneNumbers)){
            return ServerResponse.error(ResponseEnum.PHONE_IS_NULL);
        }
        QueryWrapper<Members> membersQueryWrapper = new QueryWrapper<>();
        membersQueryWrapper.eq("phone",phoneNumbers);
        Members members = membersMapper.selectOne(membersQueryWrapper);
        if (null==members){
            return ServerResponse.error(ResponseEnum.PHONE_IS_UNFIND);
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse loginBySms(Sms sms) {
        //获取前台传来的数据
        String inputCode=sms.getObj();
        String phone=sms.getMsg();
        //判断验证码是否过期
        String codeJson = RedisUtil.get(KeyUtil.buildSMSkey(phone));
        if(StringUtils.isEmpty(codeJson)){
            return ServerResponse.error(ResponseEnum.CODE_ID_UNFIND);
        }
        //取到存在Redis的验证码
        String parse = (String) JSONObject.parse(codeJson);
        Sms sms1 = JSONObject.parseObject(parse, Sms.class);
        String code = sms1.getObj();
        //判断验证码是否正确
        if (!code.equals(inputCode)){
            return ServerResponse.error(ResponseEnum.CODE_ID_WRONG);
        }
        //获取登录用户信息
        QueryWrapper<Members> membersQueryWrapper = new QueryWrapper<>();
        membersQueryWrapper.eq("phone",phone);
        Members members = membersMapper.selectOne(membersQueryWrapper);
        //返回给客户端的信息
        MembersVo membersVo = new MembersVo();
        String uuid = UUID.randomUUID().toString();
        membersVo.setId(members.getId());
        membersVo.setUuid(uuid);
        membersVo.setMembersName(members.getMembersName());
        membersVo.setRealName(members.getRealName());
        //将用户信息用base64加密
        String jsonString = JSONObject.toJSONString(membersVo);
        String encodeToString = Base64.getEncoder().encodeToString(jsonString.getBytes());
        //生成签名
        String sign = Md5Util.sign(encodeToString, SystemConst.SECRET_KEY);
        //将签名进行加密
        String s = Base64.getEncoder().encodeToString(sign.getBytes());
        //根据用户名，UUID生成key
        String s1 = KeyUtil.buildMembersRedisKey(members.getMembersName(), uuid);
        //存入Redis中，判断失效与否
        RedisUtil.setEx(s1,s,60*30);
        //返回数据（加密的用户信息+加密的签名）
        return ServerResponse.success(encodeToString+"."+s);
    }
}
