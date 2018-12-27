package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.dto.TeacherDTO;
import com.example.demo.pojo.DepartmentPO;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dto.TeacherSearchDTO;
import com.example.demo.pojo.TeacherPO;
import com.example.demo.service.ITeacherManagerService;
import com.example.demo.util.MyException;
import com.example.demo.util.Result;
import com.example.demo.util.ResultUtil;
import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/mgr/teacher")
public class TeacherManagerController {

	@Autowired
	private ITeacherManagerService teacherManagerService;
	
	@ResponseBody
	@PostMapping(value="/queryTeacherList")
	public Result queryTeacherList(@RequestBody TeacherSearchDTO dto){
		Map<String, Object> param = new HashMap<>();
		param.put("staffId", dto.getStaffId());
		param.put("name", dto.getName());
		param.put("departmentId", dto.getDepartmentId());
		PageHelper.startPage(dto.getPage(), 30);
		List<TeacherPO> list = teacherManagerService.queryTeacherList(param);
		return ResultUtil.addResult(new PageInfo(list));
	}
	
	@ResponseBody
	@PostMapping(value="/addOrUpdateTeacherInfo")
	public Result addOrUpdateTeacherInfo(@RequestBody TeacherDTO dto) throws MyException{
		Map<String, Object> param = new HashMap<>();
		param.put("id", dto.getId());
		param.put("staffId", dto.getStaffId());
		param.put("name", dto.getName());
		param.put("departmentId", dto.getDepartmentId());
		teacherManagerService.addOrUpdateTeacherInfo(param);
		return ResultUtil.success();
	}

	@ResponseBody
	@PostMapping(value="/deleteTeacherInfo")
	public Result deleteTeacherInfo(@RequestBody String param){
		JSONObject jsonObject = JSON.parseObject(param);
		JSONArray idArray = (JSONArray)jsonObject.get("ids");
		List<String> ids = JSONArray.parseArray(idArray.toString(), String.class);
		teacherManagerService.deleteTeacherInfo(ids);
		return ResultUtil.success();
	}

	@ResponseBody
	@GetMapping(value="/queryDepartmentList")
	public Result queryDepartmentList(){
		List<DepartmentPO> list = teacherManagerService.queryDepartmentList();
		return ResultUtil.addResult(list);
	}

}
