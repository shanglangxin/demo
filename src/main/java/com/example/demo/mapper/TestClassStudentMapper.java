package com.example.demo.mapper;

import com.example.demo.pojo.StudentPO;
import com.example.demo.vo.TestClassVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TestClassStudentMapper {
    List<TestClassVO> queryTestClass(Integer paperId);

    void saveTestClass(Map<String,Object> param);

    void deleteTestClass(Map<String,Object> param);

    void saveTestStudent(@Param("paperId") Integer paperId, @Param("list") List<StudentPO> studentList);

    void deleteTestStudent(Map<String,Object> param);

    void saveTestStudentMark(@Param("paperId") Integer paperId, @Param("staffId") String staffId, @Param("mark") Integer mark);
}
