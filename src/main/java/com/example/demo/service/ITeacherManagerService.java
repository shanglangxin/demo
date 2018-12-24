package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.pojo.TeacherPO;
import com.example.demo.util.MyException;

public interface ITeacherManagerService {

	List<TeacherPO> queryTeacherList(Map<String, Object> param);

	void addOrUpdateTeacherInfo(Map<String, Object> param) throws MyException;

	void deleteTeacherInfo(List<Integer> ids);

}
