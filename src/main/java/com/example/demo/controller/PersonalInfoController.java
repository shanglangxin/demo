package com.example.demo.controller;

import com.example.demo.dto.PersonalInfoDTO;
import com.example.demo.pojo.Account;
import com.example.demo.service.IPersonalInfoService;
import com.example.demo.util.MyException;
import com.example.demo.util.Result;
import com.example.demo.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/personalInfo")
public class PersonalInfoController extends BaseController {

    @Autowired
    private IPersonalInfoService personalInfoService;

    @ResponseBody
    @RequestMapping(value="/completeInfo", method=RequestMethod.POST)
    public Result completeInfo(@RequestBody PersonalInfoDTO dto) throws MyException {
        Map<String, Object> param = new HashMap<>();
        param.put("classId",dto.getClassId());
        param.put("staffId", dto.getStaffId());
        param.put("name", dto.getName());
        Account user = getUser();
        param.put("username", user.getUsername());
        personalInfoService.completeInfo(param);
        return ResultUtil.success();
    }

}
