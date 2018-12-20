package com.example.demo.util;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TitleAnnotation {
    //属性对应列名，如果没有设置Annotation属性，将不会被导出和导入
    public String titleName();
}
