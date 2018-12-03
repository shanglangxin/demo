package com.example.demo.controller;

import com.example.demo.dto.AccountDTO;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.pojo.Account;
import com.example.demo.service.ILoginService;
import com.example.demo.util.MyException;
import com.example.demo.util.Result;
import com.example.demo.util.ResultUtil;
import com.example.demo.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
        Account account = getUser();
        return ResultUtil.addResult(account);
    }

}
