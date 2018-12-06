package com.example.demo.mapper;

import com.example.demo.pojo.Question;
import com.example.demo.vo.QuestionDetailVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface QuestionMapper {
    /**
     * 添加题目
     * @param param
     */
    void addQuestion(@Param("param") Map<String,Object> param);

    /**
     * 查询题目列表
     * @param param
     * @return
     */
    List<QuestionDetailVO> queryQuestion(Map<String,Object> param);

    /**
     * 修改题目
     * @param param
     */
    void updateQuestion(Map<String,Object> param);

    void deleteQuestion(Map<String,Object> param);

    Integer queryQuestionTotalNumber(@Param("table") String table);

    List<QuestionDetailVO> queryQuestionList(@Param("table") String table, @Param("list") List<Integer> idList);

    List<Integer> queryQuestionIdList(@Param("table") String table, @Param("subjectId") Integer subjectId);
}
