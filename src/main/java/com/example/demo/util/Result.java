package com.example.demo.util;

public class Result {

    private Integer code;
    private Object data;
    private String desc;

    public Result() {
    }

    public Result(Integer code, Object data, String desc) {
        this.code = code;
        this.data = data;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
