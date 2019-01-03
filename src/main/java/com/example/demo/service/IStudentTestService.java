package com.example.demo.service;

import com.example.demo.util.MyException;
import com.example.demo.vo.StudentTestPaperRefVO;
import com.example.demo.vo.StudentTestPaperVO;

import java.util.List;
import java.util.Map;

public interface IStudentTestService {

    StudentTestPaperVO queryTestPaper(Integer paperId) throws MyException;

    Integer submitTestPaper(Map<String,Object> param);

    List<StudentTestPaperRefVO> queryTestPaperList(Map<String,Object> param);
}
