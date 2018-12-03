package com.example.demo.controller;

import com.example.demo.dto.Answer;
import com.example.demo.dto.User;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController(value="springController")
@RequestMapping(value="/iController")
public class SpringController {

    @RequestMapping(value="/index", method = RequestMethod.GET)
    public String index(){
        return "Hello World";
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response){
        if(user.getUsername().equals("123") && user.getPassword().equals("qwe")){
            return "OJBK";
        }
        return "失败";
    }

    @ResponseBody
    @RequestMapping(value = "/getAnswer", method = RequestMethod.POST)
    public String getAnswer(@RequestBody Answer answer){
        Answer aa = answer;
        return "OK";
    }

}
