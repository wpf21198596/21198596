package com.fh.shop.api.sms.entity;

import java.io.Serializable;

public class Sms implements Serializable {

    private Integer code;

    private String msg;

    private String obj;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }
}
