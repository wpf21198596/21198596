package com.fh.shop.api.consignee.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.consignee.mapper.IConsigneeMapper;
import com.fh.shop.api.consignee.po.Consignee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("consigneeService")
public class IConsigneeServiceImpl implements IConsigneeService {

    @Autowired
    private IConsigneeMapper consigneeMapper;

    @Override
    public ServerResponse findList(Long membersVoId) {
        QueryWrapper<Consignee> consigneeQueryWrapper = new QueryWrapper<>();
        consigneeQueryWrapper.eq("membersId",membersVoId);
        List<Consignee> consigneeList = consigneeMapper.selectList(consigneeQueryWrapper);
        return ServerResponse.success(consigneeList);
    }

    @Override
    public ServerResponse addConsignee(Consignee consignee) {
        consignee.setId(IdWorker.getTimeId());
        consigneeMapper.insert(consignee);
        return ServerResponse.success();
    }
}
