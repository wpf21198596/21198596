package com.fh.shop.api.order.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order implements Serializable {

    @TableId(type = IdType.INPUT)
    private String id;

    private Long userId;

    private Date createDate;

    private Integer status;

    private Date payTime;

    private BigDecimal totalMoney;

    private Integer totalCount;

    private Integer payType;

    private String addressId;
}
