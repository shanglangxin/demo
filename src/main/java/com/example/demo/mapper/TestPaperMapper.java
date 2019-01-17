package com.example.demo.mapper;

import com.example.demo.dto.TestQuestionNumDTO;
import com.example.demo.pojo.TestPaperPO;
import com.example.demo.pojo.TestPaperQuestionNumPO;
import com.example.demo.vo.StudentTestPaperRefVO;
import com.example.demo.vo.StudentTestPaperVO;
import com.example.demo.vo.TestPaperDetailVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TestPaperMapper {

    /**
     * 添加试卷
     * @param param
     */
    void addPaper(@Param("param") Map<String,Object> param);

    void updateTestPaper(Map<String,Object> param);

    List<TestPaperPO> queryPaperList(Map<String,Object> param);

    TestPaperDetailVO queryTestPaperById(Integer paperId);

    StudentTestPaperVO queryStudentTestPaperById(Integer paperId);

    List<StudentTestPaperRefVO> queryTestPaperList(Map<String,Object> param);

    void deleteTestPaper(Integer paperId);

    void addPaperQuestionNum(@Param("testPaperId") Integer id, @Param("questionNumDto") TestQuestionNumDTO questionNumDto);

    TestPaperQuestionNumPO queryTestPaperQuestionNumByPaperId(Integer paperId);
}
