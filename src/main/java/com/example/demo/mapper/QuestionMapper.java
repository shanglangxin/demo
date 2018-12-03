package com.example.demo.mapper;

import com.example.demo.pojo.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface QuestionMapper {
    void addQuestion(@Param("param") Map<String,Object> param);

    List<Question> queryQuestion(Map<String,Object> param);
}
