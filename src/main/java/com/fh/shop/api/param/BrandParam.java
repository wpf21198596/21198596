package com.fh.shop.api.param;


import com.fh.shop.api.common.Page;

public class BrandParam extends Page {
    private String brandName;

    private int orderId;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
