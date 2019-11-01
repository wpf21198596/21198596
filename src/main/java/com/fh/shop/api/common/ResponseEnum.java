package com.fh.shop.api.common;

public enum ResponseEnum {
    TIME_OUT(5002,"请求超时"),
    PAYLOG_IS_NULL(5001,"订单为空"),

    REQUEST_REPET_SEND(4020,"发送请求重复"),
    TOKEN_HEADER_IS_LOSE(4019,"token头部信息失效"),

    CODE_ID_WRONG(3018,"验证码错误，请重新输入"),
    CODE_ID_UNFIND(3017,"验证码已过期，请重新发送"),
    PHONE_IS_UNFIND(3016,"手机号未注册"),
    PHONE_IS_NULL(3015,"手机号为空"),

    SHOPLIST_IS_NULL(2015,"商品不可购买"),
    CART_IS_NULL(2014,"购物车为空"),
    SHOP_IS_NOT_UP(2013,"该商品已下架"),
    SHOP_IS_NULL(2012,"该商品不存在"),

    LOGIN_TIME_OUT(1011,"信息失效"),
    HEADERINFO_IS_CHANGE(1010,"头部信息被篡改"),
    HEADERINFO_IS_NOTALL(1009,"头部信息不完整"),
    HEADERINFO_IS_LOSE(1008,"头部信息丢失"),
    UPDATE_IS_ERROR(1007,"修改失败"),
    DEL_IS_ERROR(1006,"删除失败"),
    ADD_IS_ERROR(1005,"添加失败"),
    QUERY_IS_ERROR(1004,"信息查询失败"),
    USER_IS_LOCK(1003,"账号已锁定"),
    INPUTUSER_IS_NULL(1002,"输入账号或密码为空"),
    USER_IS_NULL(1001,"账号不存在"),
    UERPASSWORD_IS_NULL(1000,"账号或密码错误");

    private int code;

    private String msg;

    private ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
