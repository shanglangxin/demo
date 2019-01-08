package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dto.StudentMarkSearchDTO;
import com.example.demo.service.IStudentMarkMgrService;
import com.example.demo.util.Result;
import com.example.demo.util.ResultUtil;
import com.example.demo.vo.StudentMarkVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mgr/studentMark")
public class StudentMarkMgrController {

    @Autowired
    private IStudentMarkMgrService studentMarkMgrService;

    @ResponseBody
    @PostMapping(value="/queryStudentMarkList")
    public Result queryStudentMarkList(@RequestBody StudentMarkSearchDTO dto){
        Map<String, Object> param = new HashMap<>();
        param.put("classId", dto.getClassId());
        param.put("title", dto.getTitle());
        param.put("departmentId", dto.getDepartmentId());
        PageHelper.startPage(dto.getPage(), 30);
        List<StudentMarkVO> list = studentMarkMgrService.queryStudentMarkList(param);
        return ResultUtil.addResult(new PageInfo(list));
    }

    @ResponseBody
    @RequestMapping(value="/exportStudentMarkList")
    public void exportStudentMarkList(StudentMarkSearchDTO dto, HttpServletResponse response) throws IOException {
        Map<String, Object> param = new HashMap<>();
        param.put("classId", dto.getClassId());
        param.put("title", dto.getTitle());
        param.put("departmentId", dto.getDepartmentId());
        studentMarkMgrService.exportStudentMarkList(param, response);
    }

//    public Result batchDeleteStudentMark(@RequestBody String param){
//        JSONObject jsonObject = JSON.parseObject(param);
//        JSONArray idArray = (JSONArray) jsonObject.get("ids");
//        List<Integer>
//    }
    
}
