package com.fh.shop.api.cart.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartVo {

    private String totalMoney;

    private Integer totalCount;

    private List<ShopListVo> shopListVoList = new ArrayList<>();

}
