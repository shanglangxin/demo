package com.example.demo.mapper;

import com.example.demo.pojo.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MultipleEntryQuestionMapper {
    /**
     * 多项填空题添加
     * @param param
     */
    void addMultipleEntryQuestion(@Param("param") Map<String,Object> param);

    List<Question> queryMultipleEntryQuestion(Map<String,Object> param);
}
