package com.example.demo.controller;

import com.example.demo.dto.PaperDTO;
import com.example.demo.pojo.Account;
import com.example.demo.service.IPaperMgrService;
import com.example.demo.util.MyException;
import com.example.demo.util.Result;
import com.example.demo.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/paper/mgr")
public class PaperMgrController extends BaseController {

    @Autowired
    private IPaperMgrService paperMgrService;

    @ResponseBody
    @RequestMapping(value="/addOrUpdatePaper", method=RequestMethod.POST)
    public Result addOrUpdatePaper(@RequestBody PaperDTO dto) throws MyException {
        Map<String, Object> param = new HashMap<>();
        param.put("id", dto.getId());
        param.put("title", dto.getTitle());
        param.put("approach", dto.getApproach());
        param.put("singleChoiceCount", dto.getSingleChoiceCount());
        param.put("multiChoiceCount", dto.getMultiChoiceCount());
        param.put("multiEntryCount", dto.getMultiEntryCount());
        param.put("judgeCount", dto.getJudgeCount());
        param.put("completionCount", dto.getCompletionCount());
        param.put("questionList", dto.getQuestionList());
        param.put("subjectId", dto.getSubjectId());
        Account user = getUser();
        param.put("creator", user.getName());
        param.put("createTime", new Date());
        paperMgrService.addOrUpdatePaper(param);
        return ResultUtil.success();
    }

}
