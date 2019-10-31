package com.fh.shop.api.order.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("t_paylog")
public class PayLog implements Serializable {

    @TableId(type = IdType.INPUT,value = "out_trade_no")
    private String id;

    private Long userId;

    private String orderId;

    private String transactionId;

    private Date createTime;

    private Date payTime;

    private BigDecimal payMoney;

    private Integer payType;

    private Integer payStatus;
}
