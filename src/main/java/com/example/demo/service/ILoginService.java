package com.example.demo.service;

import com.example.demo.dto.AccountDTO;
import com.example.demo.util.MyException;

import javax.servlet.http.HttpServletRequest;

public interface ILoginService {

    /**
     * 登录
     * @param dto
     * @param request
     * @throws MyException
     */
    void comfirmAccount(AccountDTO dto, HttpServletRequest request) throws MyException;

    /**
     * 注册
     * @param dto
     */
    void regist(AccountDTO dto) throws MyException;
}
