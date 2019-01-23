package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import com.example.demo.dto.ExersiceDTO;
import com.example.demo.service.IStudentExerciseService;
import com.example.demo.util.Result;
import com.example.demo.util.ResultUtil;
import com.example.demo.vo.ExerciseQuestionVO;
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
@RequestMapping("/studentExercise")
public class StudentExerciseController extends BaseController{

    @Autowired
    private IStudentExerciseService studentExerciseService;

    @PostMapping(value="/queryExerciseTestPaper")
    @ResponseBody
    public Result queryExerciseTestPaper(@RequestBody String param){
        JSONObject jsonObject = (JSONObject) JSON.parse(param);
        Integer subjectId = (Integer) jsonObject.get("subjectId");
        List<ExerciseQuestionVO> list = studentExerciseService.queryExerciseTestPaper(subjectId);
        return ResultUtil.addResult(list);
    }

    @ResponseBody
    @PostMapping(value="/queryExerciseQuestionList")
    public Result queryExerciseQuestionList(@RequestBody ExersiceDTO dto){
        Map<String, Object> param = new HashMap();
        param.put("subjectId", dto.getSubjectId());
        param.put("type", dto.getType());
        param.put("questionCount", dto.getQuestionCount());
        List<ExerciseQuestionVO> list = studentExerciseService.queryExerciseQuestionList(param);
        return ResultUtil.addResult(list);
    }

    public Result submitExerciseAnswer(@RequestBody ExersiceDTO dto){

        return null;
    }


}
