package com.example.demo.util;

public class ResultUtil {

    public static Result addResult(Object object){
        return new Result(0, object, "操作成功");
    }

    public static Result success(){
        return new Result(0, null, "操作成功");
    }

    public static Result fail(){
        return new Result(0, null, "操作失败");
    }

}
