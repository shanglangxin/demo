package com.example.demo.util;

import java.util.List;

public class ImportResultVO {


    /**
     * 导入总条数
     */
    private int totalNum ;

    /**
     * 导入进程条数
     */
    private int processNum ;

    /**
     * 导入完成标记
     */
    private boolean isFinish = false;

    /**
     * 导入提示
     */
    private String tips;

    /**
     * 导入错误列表
     */
    private List errorlist;
}
