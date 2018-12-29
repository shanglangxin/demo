package com.example.demo.service;

import com.example.demo.vo.StudentMarkVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IStudentMarkMgrService {

    List<StudentMarkVO> queryStudentMarkList(Map<String,Object> param);

    void exportStudentMarkList(Map<String, Object> param, HttpServletResponse response) throws IOException;
}
