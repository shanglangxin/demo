package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dto.StudentDTO;
import com.example.demo.dto.StudentSearchDTO;
import com.example.demo.pojo.StudentPO;
import com.example.demo.service.IStudentManagerService;
import com.example.demo.util.MyException;
import com.example.demo.util.Result;
import com.example.demo.util.ResultUtil;
import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/mgr/student")
public class StudentManagerController {
	
	@Autowired
	private IStudentManagerService studentManagerService;
	
	@ResponseBody
	@PostMapping(value="/queryStudentList")
	public Result queryStudentList(@RequestBody StudentSearchDTO dto){
		Map<String, Object> param = new HashMap<>();
		param.put("staffId", dto.getStaffId());
		param.put("classId", dto.getClassId());
		param.put("departmentId", dto.getDepartmentId());
		param.put("name", dto.getName());
		PageHelper.startPage(dto.getPage(), 30);
		List<StudentPO> list = studentManagerService.queryStudentList(param);
		return ResultUtil.addResult(list);
	}
	
	@ResponseBody
	@PostMapping(value="/addOrUpdateStudent")
	public Result addOrUpdateStudent(@RequestBody StudentDTO dto) throws MyException{
		Map<String, Object> param = new HashMap<>();
		param.put("staffId", dto.getStaffId());
		param.put("classId", dto.getClassId());
		param.put("departmentId", dto.getDepartmentId());
		param.put("name", dto.getName());
		param.put("username", dto.getStaffId());
		param.put("id", dto.getId());
		studentManagerService.addOrUpdateStudent(param);
		return ResultUtil.success();
	}
	
	public Result deleteStudent(@RequestBody String param){
		JSONObject jsonObject = JSON.parseObject(param);
		JSONArray idArray = (JSONArray)jsonObject.get("ids");
		List<Integer> ids = JSONArray.parseArray(idArray.toString(), Integer.class);
		studentManagerService.deleteStudent(ids);
		return ResultUtil.success();
	}

}
