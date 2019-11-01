package com.fh.shop.api.members.biz;

import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.members.po.Members;
import com.fh.shop.api.sms.entity.Sms;

public interface IMembersService {
    ServerResponse login(Members members);

    ServerResponse loginByPhone(String phoneNumbers);

    ServerResponse loginBySms(Sms sms);
}
