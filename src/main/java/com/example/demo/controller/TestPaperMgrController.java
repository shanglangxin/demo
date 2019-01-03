package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dto.*;
import com.example.demo.pojo.AccountPO;
import com.example.demo.pojo.TestPaperPO;
import com.example.demo.service.ITestPaperMgrService;
import com.example.demo.util.MyException;
import com.example.demo.util.Result;
import com.example.demo.util.ResultUtil;
import com.example.demo.vo.TestClassVO;
import com.example.demo.vo.TestPaperDetailVO;
import com.example.demo.vo.QuestionDetailVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/testPaper/mgr")
public class TestPaperMgrController extends BaseController {

    @Autowired
    private ITestPaperMgrService testPaperMgrService;

    @ResponseBody
    @RequestMapping(value="/addOrUpdatePaper", method=RequestMethod.POST)
    public Result addOrUpdatePaper(@RequestBody TestPaperDTO dto) throws MyException {
//        if(result.hasErrors()){
//            throw new MyException(-1, "表单未填写完整");
//        }
        Map<String, Object> param = new HashMap<>();
        param.put("id", dto.getId());
        param.put("title", dto.getTitle());
        param.put("duration", dto.getDuration());
        param.put("startTime", dto.getStartTime());
        param.put("totalMark", dto.getTotalMark());
        param.put("endTime", dto.getEndTime());
        param.put("questionList", dto.getQuestionList());
        param.put("subjectId", dto.getSubjectId());
        AccountPO user = getUser();
        param.put("creator", user.getName());
        param.put("createTime", new Date());
        testPaperMgrService.addOrUpdatePaper(param);
        return ResultUtil.success();
    }
    
    @ResponseBody
    @RequestMapping(value="/autoCreateQuestionList", method=RequestMethod.POST)
    public Result autoCreateQuestionList(@RequestBody QuestionCountDTO dto) throws MyException{
    	Map<String, Object> param = new HashMap<>();
    	param.put("singleChoiceCount", dto.getSingleChoiceCount());
        param.put("multiChoiceCount", dto.getMultiChoiceCount());
        param.put("multiEntryCount", dto.getMultiEntryCount());
        param.put("judgeCount", dto.getJudgeCount());
        param.put("completionCount", dto.getCompletionCount());
        param.put("subjectId", dto.getSubjectId());
        List<QuestionDetailVO> list = testPaperMgrService.autoCreateQuestionList(param);
    	return ResultUtil.addResult(list);
    }
    
    @ResponseBody
    @RequestMapping(value="/queryPaperList", method=RequestMethod.POST)
    public Result queryPaperList(@RequestBody TestPaperSearchDTO dto){
    	Map<String, Object> param = new HashMap<>();
        param.put("id", dto.getId());
        param.put("title", dto.getTitle());
        param.put("createTime", dto.getCreateTime());
        param.put("creator", dto.getCreator());
        param.put("status", dto.getStatus());
        param.put("subjectId", dto.getSubjectId());
//        PageHelper.startPage(dto.getPage(), 30);
    	List<TestPaperPO> list = testPaperMgrService.queryPaperList(param);
    	return ResultUtil.addResult(list);
    }
    
    @ResponseBody
    @RequestMapping(value="/queryPaperDetail", method=RequestMethod.POST)
    public Result queryPaperDetail(@RequestBody String param){
        JSONObject jsonObject = JSON.parseObject(param);
    	Integer paperId = (Integer) jsonObject.get("paperId");
    	TestPaperDetailVO vo = testPaperMgrService.queryPaperDetail(paperId);
    	return ResultUtil.addResult(vo);
    }

    @ResponseBody
    @RequestMapping(value="/deleteTestPaper", method=RequestMethod.POST)
    public Result deleteTestPaper(@RequestBody String param) throws MyException {
        JSONObject jsonObject = JSON.parseObject(param);
        JSONArray idArray = (JSONArray)jsonObject.get("ids");
        List<Integer> ids = JSONArray.parseArray(idArray.toString(), Integer.class);
        testPaperMgrService.deleteTestPaper(ids);
        return ResultUtil.success();
    }

    @ResponseBody
    @PostMapping(value="/queryTestClass")
    public Result queryTestClass(@RequestBody String param){
        JSONObject jsonObject = JSON.parseObject(param);
        Integer paperId = (Integer) jsonObject.get("testPaperId");
        List<TestClassVO> list = testPaperMgrService.queryTestClass(paperId);
        return ResultUtil.addResult(list);
    }

    @ResponseBody
    @PostMapping(value="/saveTestClass")
    public Result saveTestClass(@RequestBody SaveTestClassDTO dto) throws MyException {
        Map<String, Object> param = new HashMap<>();
        param.put("testPaperId", dto.getTestPaperId());
        param.put("classList", dto.getClassList());
        testPaperMgrService.saveTestClass(param);
        return ResultUtil.success();
    }

    @ResponseBody
    @PostMapping(value="/deleteTestClass")
    public Result deleteTestClass(@RequestBody SaveTestClassDTO dto){
        Map<String, Object> param = new HashMap<>();
        param.put("testPaperId", dto.getTestPaperId());
        param.put("classList", dto.getClassList());
        testPaperMgrService.deleteTestClass(param);
        return ResultUtil.success();
    }

}
