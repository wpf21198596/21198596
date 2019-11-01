package com.fh.shop.api.consignee.biz;

import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.consignee.po.Consignee;

public interface IConsigneeService {
    ServerResponse findList(Long membersVoId);

    ServerResponse addConsignee(Consignee consignee);
}
