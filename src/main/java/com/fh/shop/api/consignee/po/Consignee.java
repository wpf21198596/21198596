package com.fh.shop.api.consignee.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Consignee implements Serializable {

    @TableId(type = IdType.INPUT)
    private String id;

    private Long membersId;

    private String consigneeName;

        private String  address;

    private String phone;

    private String email;

    private String alias;
}
