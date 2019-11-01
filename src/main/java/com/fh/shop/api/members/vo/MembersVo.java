package com.fh.shop.api.members.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MembersVo implements Serializable {

    private Long id;

    private String uuid;

    private String membersName;

    private String realName;


}
