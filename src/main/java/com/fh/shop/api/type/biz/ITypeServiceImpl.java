package com.fh.shop.api.type.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.type.mapper.ITypeMapper;
import com.fh.shop.api.type.po.Type;
import com.fh.shop.api.type.vo.TypeVo;
import com.fh.shop.api.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("typeService")
public class ITypeServiceImpl implements ITypeService {

    @Autowired
    private ITypeMapper typeMapper;

    @Override
    public ServerResponse findAllList() {
        String typeListJson = RedisUtil.get("typeList");
        if (StringUtils.isNotEmpty(typeListJson)){
            List<Type> typeList = JSONObject.parseArray(typeListJson, Type.class);
            List<TypeVo> typeVoList = buildVoList(typeList);
            return ServerResponse.success(typeVoList);
        }
        List<Type> typeList = typeMapper.selectList(null);
        List<TypeVo> typeVoList = buildVoList(typeList);
        return ServerResponse.success(typeVoList);
    }

    private List<TypeVo> buildVoList(List<Type> typeList) {
        List<TypeVo> typeVoList=new ArrayList<>();
        for (Type type : typeList) {
            TypeVo typeVo=new TypeVo();
            typeVo.setId(type.getId());
            typeVo.setpId(type.getPid());
            typeVo.setName(type.getName());
            typeVoList.add(typeVo);
        }
        return typeVoList;
    }
}
