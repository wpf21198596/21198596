package com.fh.shop.api.cart.vo;

import lombok.Data;

@Data
public class ShopListVo {

    private Long shopId;

    private String Img;

    private String shopName;

    private String price;

    private Integer count;

    private String totalPrice;

   private Integer stock;
}
