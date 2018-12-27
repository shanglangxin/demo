package com.example.demo.controller;

import com.example.demo.dto.AccountDTO;
import com.example.demo.pojo.AccountPO;
import com.example.demo.service.ILoginService;
import com.example.demo.util.MyException;
import com.example.demo.util.Result;
import com.example.demo.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value="/login")
@Controller
public class LoginController extends BaseController {

    @Autowired
    private ILoginService loginService;

    @ResponseBody
    @PostMapping(value="/logining")
    public Result login(@RequestBody AccountDTO dto, HttpServletRequest request) throws MyException {
        loginService.comfirmAccount(dto, request);
        return new Result(0, null, "OK");
    }

    @ResponseBody
    @PostMapping(value="/regist")
    public Result regist(@RequestBody AccountDTO dto, HttpServletRequest request) throws MyException {
        loginService.regist(dto);
        return new Result(0, null, "OK");
    }

    @ResponseBody
    @PostMapping(value="/getSession")
    public Result getSession() throws MyException {
        AccountPO account = getUser();
        return ResultUtil.addResult(account);
    }

}
