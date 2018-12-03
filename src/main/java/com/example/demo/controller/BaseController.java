package com.example.demo.controller;

import com.example.demo.pojo.Account;
import com.example.demo.util.MyException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseController {

    @Autowired
    private HttpServletRequest request;

    public Account getUser() throws MyException {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");
        if(user == null){
            throw new MyException(-1,"无法获取用户信息");
        }
        System.out.print(user.getUsername());
        return user;
    }
}
