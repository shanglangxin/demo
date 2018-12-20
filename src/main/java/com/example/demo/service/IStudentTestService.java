package com.example.demo.service;

import com.example.demo.util.MyException;
import com.example.demo.vo.StudentTestPaperVO;

import java.util.Map;

public interface IStudentTestService {

    StudentTestPaperVO queryTestPaper(Integer paperId) throws MyException;

    Integer submitTestPaper(Map<String,Object> param);
}
