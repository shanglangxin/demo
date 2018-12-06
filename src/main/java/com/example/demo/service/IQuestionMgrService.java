package com.example.demo.service;

import com.example.demo.pojo.SubjectPO;
import com.example.demo.vo.QuestionDetailVO;

import java.util.List;
import java.util.Map;

public interface IQuestionMgrService {

    /**
     * 添加修改题目
     * @param param
     */
    void addOrUpdateQuestion(Map<String, Object> param);

    /**
     * 获取科目
     * @param param
     * @return
     */
    List<SubjectPO> querySubjects(Map<String,Object> param);

    /**
     * 获取题目列表
     * @param param
     * @return
     */
    List<QuestionDetailVO> queryQuestionList(Map<String,Object> param);

    /**
     * 修改题目
     * @param param
     */
    void updateQuestion(Map<String,Object> param);

    /**
     * 删除题目
     * @param param
     */
    void deleteQuestion(Map<String, Object> param);
}
