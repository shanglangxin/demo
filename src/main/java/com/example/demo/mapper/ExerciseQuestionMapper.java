package com.example.demo.mapper;

import com.example.demo.vo.ExerciseOptionVO;
import com.example.demo.vo.ExerciseQuestionVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ExerciseQuestionMapper {

    List<ExerciseQuestionVO> queryExerciseQuestionList(Map<String,Object> param);

    List<ExerciseOptionVO> queryOptionByQuestionId(Integer id);
}
