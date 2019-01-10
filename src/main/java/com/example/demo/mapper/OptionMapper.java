package com.example.demo.mapper;

import com.example.demo.pojo.OptionPO;
import com.example.demo.vo.ImportQuestionVO;
import com.example.demo.vo.QuestionDetailVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OptionMapper {
    void addOptions(Map<String, Object> optionList);

    void deleteOptionByQuestionId(Map<String, Object> param);

    List<OptionPO> queryOptionListByQuestionId(QuestionDetailVO vo);

    void deleteOptionByQuestionIds(Map<String,Object> param);

    void batchAddOptions(List<ImportQuestionVO> list);
}
