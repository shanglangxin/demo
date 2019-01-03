package com.example.demo.mapper;

import com.example.demo.dto.TestClassDTO;
import com.example.demo.pojo.StudentPO;
import com.example.demo.vo.StudentVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StudentMapper {
    void addStudent(Map<String,Object> param);

    StudentPO queryStudentByUsername(String username);

	void updateStudentInfo(Map<String, Object> param);

	StudentPO queryStudentByStaffId(String id);

	List<StudentVO> queryStudentList(Map<String, Object> param);

	void deleteStudent(List<String> ids);

    List<StudentPO> queryStudentByClassId(@Param("list") List<TestClassDTO> classList);
}
