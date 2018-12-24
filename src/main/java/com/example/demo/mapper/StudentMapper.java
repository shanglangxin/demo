package com.example.demo.mapper;

import com.example.demo.pojo.StudentPO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StudentMapper {
    void addStudent(Map<String,Object> param);

    StudentPO queryStudentByUsername(String username);

	void updateStudentInfo(Map<String, Object> param);

	StudentPO queryStudentById(Integer id);

	List<StudentPO> queryStudentList(Map<String, Object> param);

	void deleteStudent(List<Integer> ids);
}
