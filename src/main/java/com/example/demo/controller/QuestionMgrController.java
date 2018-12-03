package com.example.demo.controller;

import com.example.demo.dto.QestionDTO;
import com.example.demo.dto.SearchQuesiotnDTO;
import com.example.demo.pojo.Account;
import com.example.demo.pojo.Question;
import com.example.demo.pojo.SubjectPO;
import com.example.demo.service.IQuestionMgrService;
import com.example.demo.util.MyException;
import com.example.demo.util.Result;
import com.example.demo.util.ResultUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/question/mgr")
@Controller
public class QuestionMgrController extends BaseController {

    @Autowired
    private IQuestionMgrService questionMgrService;

    @ResponseBody
    @PostMapping(value = "/addQuestion")
    public Result addQuestion(@RequestBody QestionDTO dto) throws MyException {
        Account user = getUser();
        Map<String, Object> param = new HashMap<>();
        param.put("title", dto.getTitle());
        param.put("type", dto.getType());
        param.put("multiAnswer", dto.getMultiAnswer());
        param.put("singleAnswer", dto.getSingleAnswer());
        param.put("list", dto.getOptionList());
        param.put("showTitle", dto.getShowTitle());
        param.put("creator",user.getName());
        param.put("createTime", new Date());
        questionMgrService.addQuestion(param);
        return ResultUtil.success();
    }

    @ResponseBody
    @RequestMapping(value="/querySubjects", method=RequestMethod.GET)
    public Result querySubjects() throws MyException {
        Account user = getUser();
        Map<String, Object> param = new HashMap<>();
        param.put("username", user.getUsername());
        param.put("type", user.getType());
        List<SubjectPO> list = questionMgrService.querySubjects(param);
        return ResultUtil.addResult(list);
    }

    @ResponseBody
    @RequestMapping(value="/queryQuestionList", method=RequestMethod.POST)
    public Result queryQuesitonList(@RequestBody SearchQuesiotnDTO dto){
        Map<String, Object> param = new HashMap<>();
        param.put("type", dto.getType());
        param.put("date", dto.getDate());
        param.put("creator", dto.getCreator());
        param.put("subject", dto.getSubject());
        param.put("page", 1);
        List<Question> list = questionMgrService.queryQuestionList(param);
        return ResultUtil.addResult(new PageInfo(list));
    }

}
