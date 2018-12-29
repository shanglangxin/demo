package com.example.demo.mapper;

import com.example.demo.vo.StudentMarkVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TestPaperStudentRefMapper {
    List<StudentMarkVO> queryStudentMarkList(Map<String,Object> param);
}
