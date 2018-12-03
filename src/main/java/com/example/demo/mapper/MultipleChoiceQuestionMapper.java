package com.example.demo.mapper;

import com.example.demo.pojo.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MultipleChoiceQuestionMapper {
    /**
     * 添加多选
     * @param param
     */
    void addMultipleChoiceQuestion(@Param("param") Map<String,Object> param);

    List<Question> queryMultipleChoiceQuestion(Map<String,Object> param);
}
