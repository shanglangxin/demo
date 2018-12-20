package com.example.demo.dto;

import com.example.demo.pojo.TestQuestionOptionPO;

import java.util.List;

public class SubmitPaperQuestionDTO {

    private Integer id;

    private String singleAnswer;

    private List<String> multiAnswer;

    private String answer;

    private Integer type;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getSingleAnswer() {
        return singleAnswer;
    }

    public List<String> getMultiAnswer() {
        return multiAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public Integer getType() {
        return type;
    }

    public void setSingleAnswer(String singleAnswer) {
        this.singleAnswer = singleAnswer;
    }

    public void setMultiAnswer(List<String> multiAnswer) {
        this.multiAnswer = multiAnswer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
