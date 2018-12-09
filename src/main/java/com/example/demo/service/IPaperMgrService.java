package com.example.demo.service;

import com.example.demo.pojo.PaperPO;
import com.example.demo.pojo.Question;
import com.example.demo.util.MyException;
import com.example.demo.vo.PaperDetailVO;
import com.example.demo.vo.QuestionDetailVO;

import java.util.List;
import java.util.Map;

public interface IPaperMgrService {
    void addOrUpdatePaper(Map<String,Object> param) throws MyException;

	List<QuestionDetailVO> autoCreateQuestionList(Map<String, Object> param) throws MyException;

	List<PaperPO> queryPaperList(Map<String, Object> param);

	PaperDetailVO queryPaperDetail(Integer paperId);
}
