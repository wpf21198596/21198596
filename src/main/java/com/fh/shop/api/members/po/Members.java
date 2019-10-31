package com.fh.shop.api.members.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class Members implements Serializable {

    private Long id;

    private String membersName;

    private String realName;

    private String password;

    private String phone;

    private String email;

}
