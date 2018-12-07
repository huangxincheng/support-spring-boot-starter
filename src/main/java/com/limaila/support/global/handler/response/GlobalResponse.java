package com.limaila.support.global.handler.response;

import com.limaila.support.global.handler.enums.GlobalEnum;

/**
 * Author: huangxincheng
 * <p>
 * <p>
 **/
public class GlobalResponse<T extends Object> {

    private T data;

    private GlobalResult result;

    public static class GlobalResult {
        private Integer code;

        private String msg;

        private Long costTime;

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

        public Long getCostTime() {
            return costTime;
        }

        public void setCostTime(Long costTime) {
            this.costTime = costTime;
        }
    }


    private GlobalResponse(Integer code, String msg, Long costTime, T data) {
        GlobalResult result = new GlobalResult();
        result.setCode(code);
        result.setMsg(msg);
        result.setCostTime(costTime);
        this.data = data;
        this.result = result;
    }

    private GlobalResponse(Integer code ,String errorMsg) {
        GlobalResult result = new GlobalResult();
        result.setCode(code);
        result.setMsg(errorMsg);
        this.result = result;
    }

    public static <T extends Object> GlobalResponse toSucc(Long costTime ,T data) {
        return new GlobalResponse(GlobalEnum.SUCC.getCode(), GlobalEnum.SUCC.getMsg(), costTime, data);
    }

    public static GlobalResponse toFail(Exception ex) {
        return new GlobalResponse(GlobalEnum.FAIL.getCode(), ex.getMessage());
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public GlobalResult getResult() {
        return result;
    }

    public void setResult(GlobalResult result) {
        this.result = result;
    }
}

