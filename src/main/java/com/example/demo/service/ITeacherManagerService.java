package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.dto.TeachClassDTO;
import com.example.demo.dto.TeachSubjectDTO;
import com.example.demo.pojo.DepartmentPO;
import com.example.demo.pojo.SubjectPO;
import com.example.demo.pojo.TeacherPO;
import com.example.demo.util.MyException;
import com.example.demo.vo.TestClassVO;

public interface ITeacherManagerService {

	List<TeacherPO> queryTeacherList(Map<String, Object> param);

	void addOrUpdateTeacherInfo(Map<String, Object> param) throws MyException;

	void deleteTeacherInfo(List<String> ids);

    List<DepartmentPO> queryDepartmentList();

    void addTeachClass(TeachClassDTO dto) throws MyException;

	List<TestClassVO> queryTeachClass(String staffId);

	void deleteTeachClass(TeachClassDTO dto);

	void addTeachSubject(TeachSubjectDTO dto) throws MyException;

	List<SubjectPO> querySubjectList(String title);
}
