package com.example.demo.mapper;

import com.example.demo.dto.OptionDTO;
import com.example.demo.pojo.Option;
import com.example.demo.vo.QuestionDetailVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OptionMapper {
    void addOptions(Map<String, Object> optionList);

    void deleteOptionByQuestionId(Map<String, Object> param);

    List<Option> queryOptionListByQuestionId(QuestionDetailVO vo);

    void deleteOptionByQuestionIds(Map<String,Object> param);
}
