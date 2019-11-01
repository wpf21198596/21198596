package com.fh.shop.api.order.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("t_orderItem")
public class OrderItem implements Serializable {

    private String orderId;

    private Long userId;

    private Long shopId;

    private String shopName;

    private BigDecimal price;

    private BigDecimal totalPrice;

    private String img;

    private Integer count;

}
