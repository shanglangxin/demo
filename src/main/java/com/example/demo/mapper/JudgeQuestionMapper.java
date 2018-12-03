package com.example.demo.mapper;

import com.example.demo.pojo.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface JudgeQuestionMapper {
    /**
     * 添加判断题
     * @param param
     */
    void addJudgeQuestion(@Param("param") Map<String,Object> param);

    /**
     * 查询判断题
     * @param param
     * @return
     */
    List<Question> queryJudgeQuestion(Map<String,Object> param);
}
