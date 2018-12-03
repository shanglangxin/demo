package com.example.demo.mapper;

import com.example.demo.pojo.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CompletionQuestionMapper {
    /**
     * 填空题
     * @param param
     */
    void addCompletionQuestion(@Param("param") Map<String,Object> param);

    List<Question> queryCompletionQuestion(Map<String,Object> param);
}
