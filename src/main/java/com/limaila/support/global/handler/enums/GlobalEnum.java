package com.limaila.support.global.handler.enums;

/**
 * Author: huangxincheng
 * <p>
 * <p>
 **/
public enum GlobalEnum {

    SUCC(200, "SUCCESS"),

    FAIL(500, "服务器异常,请稍后再试"),
    ;

    private Integer code;

    private String msg;

    GlobalEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

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

}
