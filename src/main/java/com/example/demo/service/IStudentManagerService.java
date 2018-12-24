package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.pojo.StudentPO;
import com.example.demo.util.MyException;

public interface IStudentManagerService {

	List<StudentPO> queryStudentList(Map<String, Object> param);

	void addOrUpdateStudent(Map<String, Object> param) throws MyException;

	void deleteStudent(List<Integer> ids);

}
