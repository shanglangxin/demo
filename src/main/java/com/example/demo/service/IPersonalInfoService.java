package com.example.demo.service;

import com.example.demo.util.MyException;

import java.util.Map;

public interface IPersonalInfoService {
    void completeInfo(Map<String,Object> param) throws MyException;
}
