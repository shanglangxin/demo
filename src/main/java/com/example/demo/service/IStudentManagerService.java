package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.pojo.ClazzPO;
import com.example.demo.util.MyException;
import com.example.demo.vo.StudentVO;

public interface IStudentManagerService {

	List<StudentVO> queryStudentList(Map<String, Object> param);

	void addOrUpdateStudent(Map<String, Object> param) throws MyException;

	void deleteStudent(List<String> ids);

    List<ClazzPO> queryDepartmentClass(Integer departmentId);
}
