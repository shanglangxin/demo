package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.SearchQuesiotnDTO;
import com.example.demo.pojo.Account;
import com.example.demo.pojo.Question;
import com.example.demo.pojo.SubjectPO;
import com.example.demo.service.IQuestionMgrService;
import com.example.demo.util.MyException;
import com.example.demo.util.Result;
import com.example.demo.util.ResultUtil;
import com.example.demo.vo.QuestionDetailVO;
import com.github.pagehelper.PageInfo;
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
    @PostMapping(value = "/addOrUpdateQuestion")
    public Result addOrUpdateQuestion(@RequestBody QuestionDTO dto) throws MyException {
        Account user = getUser();
        Map<String, Object> param = new HashMap<>();
        param.put("id",dto.getId());
        param.put("title", dto.getTitle());
        param.put("type", dto.getType());
        param.put("multiAnswer", dto.getMultiAnswer());
        param.put("singleAnswer", dto.getSingleAnswer());
        param.put("subjectId", dto.getSubjectId());
        param.put("list", dto.getOptionList());
        param.put("showTitle", dto.getShowTitle());
        param.put("creator",user.getName());
        param.put("createTime", new Date());
        questionMgrService.addOrUpdateQuestion(param);
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
        List<QuestionDetailVO> list = questionMgrService.queryQuestionList(param);
        return ResultUtil.addResult(new PageInfo(list));
    }

    @ResponseBody
    @RequestMapping(value="/updateQuestion", method=RequestMethod.POST)
    public Result updateQuestion(QuestionDTO dto){
        Map<String, Object> param = new HashMap<>();
        param.put("id",dto.getId());
        param.put("title", dto.getTitle());
        param.put("type", dto.getType());
        param.put("multiAnswer", dto.getMultiAnswer());
        param.put("singleAnswer", dto.getSingleAnswer());
        param.put("list", dto.getOptionList());
        param.put("subjectId", dto.getSubjectId());
        param.put("showTitle", dto.getShowTitle());
        questionMgrService.updateQuestion(param);
        return ResultUtil.success();
    }

    @ResponseBody
    @RequestMapping(value="/deleteQuestion", method=RequestMethod.POST)
    public Result deleteQuestion(@RequestBody String param){
        JSONObject param1 = JSON.parseObject(param);
        JSONArray idArray = (JSONArray) param1.get("ids");
        Integer type = (Integer) param1.get("type");
        Map<String, Object> map = new HashMap<>();
        map.put("ids", idArray);
        map.put("type", type);
        questionMgrService.deleteQuestion(map);
        return ResultUtil.success();
    }

}
