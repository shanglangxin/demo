package com.example.demo.dto;

import com.example.demo.vo.TestPaperQuestionDetailVO;

import java.util.List;

public class SubmitPaperDTO {

    private Integer id;
    private List<SubmitPaperQuestionDTO> questionList;

    public List<SubmitPaperQuestionDTO> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<SubmitPaperQuestionDTO> questionList) {
        this.questionList = questionList;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
