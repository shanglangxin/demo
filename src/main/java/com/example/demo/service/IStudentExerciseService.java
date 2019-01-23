package com.example.demo.service;

import com.example.demo.vo.ExerciseQuestionVO;

import java.util.List;
import java.util.Map;

public interface IStudentExerciseService {
    List<ExerciseQuestionVO> queryExerciseTestPaper(Integer subjectId);

    List<ExerciseQuestionVO> queryExerciseQuestionList(Map<String,Object> param);
}
