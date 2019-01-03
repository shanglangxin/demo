package com.example.demo.service;

import com.example.demo.pojo.TestPaperPO;
import com.example.demo.util.MyException;
import com.example.demo.vo.TestClassVO;
import com.example.demo.vo.TestPaperDetailVO;
import com.example.demo.vo.QuestionDetailVO;

import java.util.List;
import java.util.Map;

public interface ITestPaperMgrService {
    void addOrUpdatePaper(Map<String,Object> param) throws MyException;

	List<QuestionDetailVO> autoCreateQuestionList(Map<String, Object> param) throws MyException;

	List<TestPaperPO> queryPaperList(Map<String, Object> param);

	TestPaperDetailVO queryPaperDetail(Integer paperId);

    List<TestClassVO> queryTestClass(Integer paperId);

	void saveTestClass(Map<String,Object> param) throws MyException;

	void deleteTestClass(Map<String,Object> param);

    void deleteTestPaper(List<Integer> ids) throws MyException;
}
