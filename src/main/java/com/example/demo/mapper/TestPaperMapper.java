package com.example.demo.mapper;

import com.example.demo.pojo.TestPaperPO;
import com.example.demo.vo.TestPaperDetailVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TestPaperMapper {

    /**
     * 添加试卷
     * @param param
     */
    void addPaper(@Param("param") Map<String,Object> param);

    void updateTestPaper(Map<String,Object> param);

    List<TestPaperPO> queryPaperList(Map<String,Object> param);

    TestPaperDetailVO queryTestPaperById(Integer paperId);
}
