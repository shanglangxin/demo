package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.PaperDTO;
import com.example.demo.dto.PaperSearchDTO;
import com.example.demo.dto.QuestionCountDTO;
import com.example.demo.pojo.Account;
import com.example.demo.pojo.PaperPO;
import com.example.demo.pojo.Question;
import com.example.demo.service.IPaperMgrService;
import com.example.demo.util.MyException;
import com.example.demo.util.Result;
import com.example.demo.util.ResultUtil;
import com.example.demo.vo.PaperDetailVO;
import com.example.demo.vo.QuestionDetailVO;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

@Controller
@RequestMapping(value="/paper/mgr")
public class PaperMgrController extends BaseController {

    @Autowired
    private IPaperMgrService paperMgrService;

    @ResponseBody
    @RequestMapping(value="/addOrUpdatePaper", method=RequestMethod.POST)
    public Result addOrUpdatePaper(@RequestBody @Valid PaperDTO dto, BindingResult result) throws MyException {
        Map<String, Object> param = new HashMap<>();
        param.put("id", dto.getId());
        param.put("title", dto.getTitle());
        param.put("duration", dto.getDuration());
        param.put("startTime", dto.getStartTime());
        param.put("endTime", dto.getEndTime());
//        param.put("approach", dto.getApproach());
//        param.put("singleChoiceCount", dto.getSingleChoiceCount());
//        param.put("multiChoiceCount", dto.getMultiChoiceCount());
//        param.put("multiEntryCount", dto.getMultiEntryCount());
//        param.put("judgeCount", dto.getJudgeCount());
//        param.put("completionCount", dto.getCompletionCount());
        param.put("questionList", dto.getQuestionList());
        param.put("subjectId", dto.getSubjectId());
        Account user = getUser();
        param.put("creator", user.getName());
        param.put("createTime", new Date());
        paperMgrService.addOrUpdatePaper(param);
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
        List<QuestionDetailVO> list = paperMgrService.autoCreateQuestionList(param);
    	return ResultUtil.addResult(list);
    }
    
    @ResponseBody
    @RequestMapping(value="/queryPaperList", method=RequestMethod.POST)
    public Result queryPaperList(@RequestBody PaperSearchDTO dto){
    	Map<String, Object> param = new HashMap<>();
        param.put("id", dto.getId());
        param.put("title", dto.getTitle());
        param.put("createTime", dto.getCreateTime());
        param.put("creator", dto.getCreator());
        param.put("status", dto.getStatus());
        PageHelper.startPage(dto.getPage(), 30);
    	List<PaperPO> list = paperMgrService.queryPaperList(param);
    	return ResultUtil.addResult(list);
    }
    
    @ResponseBody
    @RequestMapping(value="/queryPaperDetail", method=RequestMethod.POST)
    public Result queryPaperDetail(@RequestBody String id){
    	Integer paperId = (Integer) JSON.parseObject(id).get("id");
    	PaperDetailVO vo = paperMgrService.queryPaperDetail(paperId);
    	return ResultUtil.addResult(vo);
    }

}
