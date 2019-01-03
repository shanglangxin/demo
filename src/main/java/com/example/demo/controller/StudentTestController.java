package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dto.StudentTestPaperDTO;
import com.example.demo.dto.SubmitPaperDTO;
import com.example.demo.dto.TestPaperQuestionDTO;
import com.example.demo.pojo.TestPaperPO;
import com.example.demo.service.IStudentTestService;
import com.example.demo.util.MyException;
import com.example.demo.util.Result;
import com.example.demo.util.ResultUtil;
import com.example.demo.vo.StudentTestPaperRefVO;
import com.example.demo.vo.StudentTestPaperVO;
import com.example.demo.vo.TestPaperDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/studentTest")
public class StudentTestController extends BaseController {

    @Autowired
    private IStudentTestService studentTestService;

    @ResponseBody
    @PostMapping(value="/queryTestPaper")
    public Result queryTestPaper(@RequestBody String param) throws MyException {
        JSONObject jsonObject = JSON.parseObject(param);
        Integer paperId = (Integer) jsonObject.get("paperId");
        StudentTestPaperVO paperDetailVO = studentTestService.queryTestPaper(paperId);
        return ResultUtil.addResult(paperDetailVO);
    }

    @ResponseBody
    @PostMapping(value="/submitTestPaper")
    public Result submitTestPaper(@RequestBody SubmitPaperDTO dto) throws MyException {
        Map<String, Object> param = new HashMap<>();
        param.put("paperId", dto.getId());
        param.put("questionList", dto.getQuestionList());
        param.put("staffId", super.getUser().getStaffId());
        Integer mark = studentTestService.submitTestPaper(param);
        return ResultUtil.addResult(mark);
    }

    @ResponseBody
    @PostMapping(value="/queryTestPaperList")
    public Result queryTestPaperList(@RequestBody StudentTestPaperDTO dto) throws MyException {
        Map<String, Object> param = new HashMap<>();
        param.put("staffId", super.getUser().getStaffId());
        param.put("subjectId", dto.getSubjectId());
        param.put("title", dto.getTitle());
        List<StudentTestPaperRefVO> list = studentTestService.queryTestPaperList(param);
        return ResultUtil.addResult(list);
    }

}
