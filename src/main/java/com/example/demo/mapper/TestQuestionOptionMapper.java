package com.example.demo.mapper;

import com.example.demo.dto.OptionDTO;
import com.example.demo.dto.TestPaperQuestionDTO;
import com.example.demo.pojo.TestQuestionOptionPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestQuestionOptionMapper {


    void addTestOption(@Param("questionId") Integer id, @Param("list") List<OptionDTO> optionList, @Param("type") Integer type);

    void deleteOptionByTestQuestionIds(@Param("list") List<Integer> questionIdList);

    List<TestQuestionOptionPO> queryTestOptionByTestQuestionId(Integer id);
}
