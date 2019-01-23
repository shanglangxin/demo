package com.example.demo.service.imp;

import com.example.demo.mapper.ExerciseQuestionMapper;
import com.example.demo.pojo.TestQuestionOptionPO;
import com.example.demo.service.IStudentExerciseService;
import com.example.demo.util.QuestionTypeUtil;
import com.example.demo.vo.ExerciseOptionVO;
import com.example.demo.vo.ExerciseQuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service("studentExerciseService")
public class StudentExerciseServiceImp implements IStudentExerciseService {

    @Autowired
    private ExerciseQuestionMapper exerciseQuestionMapper;

    @Override
    public List<ExerciseQuestionVO> queryExerciseTestPaper(Integer subjectId) {

        return null;
    }

    @Override
    public List<ExerciseQuestionVO> queryExerciseQuestionList(Map<String, Object> param) {
        List<ExerciseQuestionVO> list = exerciseQuestionMapper.queryExerciseQuestionList(param);
        for(ExerciseQuestionVO vo : list){
            if(vo.getType() == QuestionTypeUtil.MULTIPLE_ENTRY_QUESTION){
                List<String> answerList = Arrays.asList(vo.getAnswer().split(","));
                for(int i =0 ; i<answerList.size(); i++){
                    answerList.set(i,"");
                }
                vo.setMultiAnswer(answerList);
            }else if(vo.getType() == QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION){
                vo.setMultiAnswer(new ArrayList<>());
            } else{
                vo.setSingleAnswer("");
            }
            if(vo.getType() == QuestionTypeUtil.SINGLE_CHOICE_QUESTION || vo.getType() == QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION){
                List<ExerciseOptionVO> optionList = exerciseQuestionMapper.queryOptionByQuestionId(vo.getId());
                vo.setOptionList(optionList);
            }
        }
        return list;
    }

}
