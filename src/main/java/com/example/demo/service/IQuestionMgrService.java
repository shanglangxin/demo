package com.example.demo.service;

import com.example.demo.pojo.Question;
import com.example.demo.pojo.SubjectPO;

import java.util.List;
import java.util.Map;

public interface IQuestionMgrService {

    /**
     * 添加题目
     * @param param
     */
    void addQuestion(Map<String, Object> param);

    /**
     * 获取科目
     * @param param
     * @return
     */
    List<SubjectPO> querySubjects(Map<String,Object> param);

    List<Question> queryQuestionList(Map<String,Object> param);
}
