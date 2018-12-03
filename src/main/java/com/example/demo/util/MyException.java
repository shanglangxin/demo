package com.example.demo.util;

public class MyException extends Throwable {

    private Integer code;

    public MyException() {
        super();
    }

    public MyException(Integer code, String desc){
        super(desc);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
