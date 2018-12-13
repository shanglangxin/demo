package com.example.demo.mapper;

import com.example.demo.dto.TestPaperQuestionDTO;
import com.example.demo.vo.TestPaperQuestionDetailVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestPaperQuestionMapper {

    void addTestPaperQuestions(@Param("list") List<TestPaperQuestionDTO> list, @Param("paperId") Integer id);

    void deleteQuestionsByPaperId(Integer id);

    List<TestPaperQuestionDetailVO> queryTestQuestionByPaperId(Integer id);

    List<Integer> queryTestQuestionIdsByPaperId(Integer id);
}
